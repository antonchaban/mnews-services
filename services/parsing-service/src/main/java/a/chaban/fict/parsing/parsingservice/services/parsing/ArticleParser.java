package a.chaban.fict.parsing.parsingservice.services.parsing;


import a.chaban.fict.parsing.parsingservice.entities.Article;
import a.chaban.fict.parsing.parsingservice.services.translation.APIConnector;
import a.chaban.fict.parsing.parsingservice.services.translation.TranslateAPIParser;
import com.sun.syndication.io.FeedException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ArticleParser {
    private final ArticleRssParser articleRssParser;
    private final TranslateAPIParser translateAPIParser;
    private final String PRAVDA_LINK = "https://www.pravda.com.ua/rss/";
    private final String CNN_LINK = "http://rss.cnn.com/rss/cnn_topstories.rss";
    private final String FOX_LINK = "https://moxie.foxnews.com/google-publisher/world.xml";
    private final String UNIAN_LINK = "https://rss.unian.net/site/news_ukr.rss";

    private final APIConnector apiConnector;

    @Value("${category.url}")
    public String categoryUrl; // maybe url?
    public List<Article> parseArticle(String link) throws FeedException, IOException, ParseException { // need to check for duplicates in article service
        ArrayList<Article> listFromRss = articleRssParser.doParse(link);
        for (Article articleRss : listFromRss) {
            switch (link) {
                case PRAVDA_LINK -> parseAssist(articleRss, "PRAVDA");
                case CNN_LINK -> parseAssist(articleRss, "CNN");
                case FOX_LINK -> parseAssist(articleRss, "FOX NEWS");
                case UNIAN_LINK -> parseAssist(articleRss, "УНІАН");
            }
            if (articleRss.getArticleDate() != null) {
                articleRss.getCategories()
                        .add(consumeCategory(articleRss.getTitle_en() + articleRss.getDescription_en()));
                System.out.println(articleRss);
            }

        }
        return listFromRss.isEmpty() ? null : listFromRss;
    }

    public Article.Category consumeCategory(String text) throws IOException {
        HttpURLConnection conn = apiConnector.setConnection(categoryUrl + "predict");
        try {
            String jsonInputString = "{\"text\":" + "\"" + text.replace("\"", "\\\"")
                    + "\"}";
            return getCategory(conn, jsonInputString);
        } finally {
            apiConnector.endConnection(conn);
        }
    }

    private Article.Category getCategory(HttpURLConnection connection, String jsonInputString) throws IOException {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
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
            // Handle unsuccessful response
            System.out.println("HTTP POST request failed with response code: " + responseCode);
            return Article.Category.UNKNOWN;
        }
    }

    private void parseAssist(Article articleRss, String source) throws IOException, ParseException {
        try {
//            articleRss.setUserId(customerRepo.findById(ID).get());
            if (articleRss.getSource().isEmpty()) articleRss.setSource(source);
            try {
                if (articleRss.getTitle_en().isEmpty()) {
                    addTranslation(articleRss, "uk", "en");
                } else addTranslation(articleRss, "en", "uk");
            } catch (NullPointerException e) {
                addTranslation(articleRss, "uk", "en");
            }
        } catch (NullPointerException e) {
            System.err.println("Error in parseAssist");
        }
    }

    private void addTranslation(Article article, String sourceLang, String targetLang) throws IOException, ParseException {
        switch (sourceLang) {
            case "uk" -> {
                article.setTitle_en(translateAPIParser.doParse(article.getTitle_ua(), sourceLang, targetLang));
                article.setDescription_en(translateAPIParser.doParse(article.getDescription_ua(), sourceLang, targetLang));
            }
            case "en" -> {
                article.setTitle_ua(translateAPIParser.doParse(article.getTitle_en(), sourceLang, targetLang));
                article.setDescription_ua(translateAPIParser.doParse(article.getDescription_en(), sourceLang, targetLang));
            }
        }
//        articleRepo.save(article);
    }
}