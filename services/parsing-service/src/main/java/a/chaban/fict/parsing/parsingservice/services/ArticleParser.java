package a.chaban.fict.parsing.parsingservice.services;


import a.chaban.fict.parsing.parsingservice.entities.Article;
import com.sun.syndication.io.FeedException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleParser {
    private final ArticleRssParser articleRssParser;
    private final TranslateAPIParser translateAPIParser;
    private final String PRAVDA_LINK = "https://www.pravda.com.ua/rss/";
    private final String CNN_LINK = "http://rss.cnn.com/rss/cnn_topstories.rss";
    private final String FOX_LINK = "https://moxie.foxnews.com/google-publisher/world.xml";
    private final String UNIAN_LINK = "https://rss.unian.net/site/news_ukr.rss";

    public List<Article> parseArticle(String link) throws FeedException, IOException, ParseException { // todo removed check for dublicates in db
        ArrayList<Article> listFromRss = articleRssParser.doParse(link);
        for (Article articleRss : listFromRss) {
//            if (articleRepo.findArticleByArticleLink(articleRss.getArticleLink()) == null) {
            switch (link) {
                case PRAVDA_LINK -> parseAssist(articleRss, "PRAVDA");
                case CNN_LINK -> parseAssist(articleRss, "CNN");
                case FOX_LINK -> parseAssist(articleRss, "FOX NEWS");
                case UNIAN_LINK -> parseAssist(articleRss, "УНІАН");
            }
            if (articleRss.getArticleDate() != null) {
/*                articleRss.getCategories()
                        .add(categoryParser
                                .doParse(articleRss.getArticleTitleEn() + articleRss.getArticleDescriptionEn()));*/ // todo add categories from category detector service
                System.out.println(articleRss);
            }
//            }

        }
        return listFromRss.isEmpty() ? null : listFromRss;
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
