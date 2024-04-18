package a.chaban.fict.parsing.parsingservice.services.parsing;


import a.chaban.fict.parsing.parsingservice.entities.Article;
import a.chaban.fict.parsing.parsingservice.services.messaging.RabbitMQArticleProducer;
import a.chaban.fict.parsing.parsingservice.services.translation.APIConnector;
import a.chaban.fict.parsing.parsingservice.services.translation.TranslateAPIParser;
import com.sun.syndication.io.FeedException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ArticleParser {
    private static final String PRAVDA_LINK = "https://www.pravda.com.ua/rss/";
    private static final String CNN_LINK = "http://rss.cnn.com/rss/cnn_topstories.rss";
    private static final String FOX_LINK = "https://moxie.foxnews.com/google-publisher/world.xml";
    private static final String UNIAN_LINK = "https://rss.unian.net/site/news_ukr.rss";

    private final ArticleRssParser articleRssParser;
    private final TranslateAPIParser translateAPIParser;
    private final APIConnector apiConnector;
    private final RabbitMQArticleProducer rabbitMQArticleProducer;

    private static final String UK_LANG = "uk";
    private static final String EN_LANG = "en";

    @Value("${category.url}")
    private String categoryUrl;

    @Value("${id.pravda}")
    private Long PRAVDA_ID;
    @Value("${id.cnn}")
    private Long CNN_ID;
    @Value("${id.fox}")
    private Long FOX_ID;
    @Value("${id.unian}")
    private Long UNIAN_ID;

    public void parseArticle(String link) throws FeedException, IOException, ParseException {
        ArrayList<Article> listFromRss = articleRssParser.doParse(link);
        for (Article articleRss : listFromRss) {
            if (isArticleValid(articleRss)) {
                parseSource(articleRss, link);
                articleRss.getCategories().add(consumeCategory(articleRss.getTitle_en() + articleRss.getDescription_en()));
                rabbitMQArticleProducer.sendArticleEntity(articleRss);
                System.out.println(articleRss);
            } else {
                System.out.println("Skipping article due to null/empty fields: " + articleRss);
            }
        }
    }

    private boolean isArticleValid(Article article) {
        return article.getArticleDate() != null &&
                article.getTitle_en() != null && !article.getTitle_en().isEmpty() &&
                article.getDescription_en() != null && !article.getDescription_en().isEmpty();
    }


    private void parseSource(Article article, String link) throws IOException, ParseException {
        switch (link) {
            case PRAVDA_LINK -> parseAssist(article, "PRAVDA", PRAVDA_ID);
            case CNN_LINK -> parseAssist(article, "CNN", CNN_ID);
            case FOX_LINK -> parseAssist(article, "FOX NEWS", FOX_ID);
            case UNIAN_LINK -> parseAssist(article, "УНІАН", UNIAN_ID);
        }
    }

    private Article.Category consumeCategory(String text) throws IOException {
        HttpURLConnection conn = apiConnector.setConnection(categoryUrl + "predict");
        try {
            String jsonInputString = "{\"text\":" + "\"" + text.replace("\"", "\\\"") + "\"}";
            return getCategory(conn, jsonInputString);
        } finally {
            apiConnector.endConnection(conn);
        }
    }

    private Article.Category getCategory(HttpURLConnection connection, String jsonInputString) throws IOException {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                String categoryString = jsonResponse.getString("category");
                return Article.Category.valueOf(categoryString.toUpperCase());
            }
        } else {
            System.out.println("HTTP POST request failed with response code: " + responseCode);
            return Article.Category.UNKNOWN;
        }
    }

    private void parseAssist(Article article, String source, Long id) throws IOException, ParseException {
        try {
            article.setUserId(id);
            if (article.getSource().isEmpty()) article.setSource(source);
            try {
                if (article.getTitle_en().isEmpty()) {
                    addTranslation(article, UK_LANG, EN_LANG);
                } else addTranslation(article, EN_LANG, UK_LANG);
            } catch (NullPointerException e) {
                addTranslation(article, UK_LANG, EN_LANG);
            }
        } catch (NullPointerException e) {
            System.err.println("Error in parseAssist");
        }
    }

    private void addTranslation(Article article, String sourceLang, String targetLang) throws IOException, ParseException {
        switch (sourceLang) {
            case UK_LANG -> {
                article.setTitle_en(translateAPIParser.doParse(article.getTitle_ua(), sourceLang, targetLang));
                article.setDescription_en(translateAPIParser.doParse(article.getDescription_ua(), sourceLang, targetLang));
            }
            case EN_LANG -> {
                article.setTitle_ua(translateAPIParser.doParse(article.getTitle_en(), sourceLang, targetLang));
                article.setDescription_ua(translateAPIParser.doParse(article.getDescription_en(), sourceLang, targetLang));
            }
        }
    }
}
