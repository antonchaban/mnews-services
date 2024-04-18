package a.chaban.fict.parsing.parsingservice.services;

import a.chaban.fict.parsing.parsingservice.services.parsing.ArticleParser;
import com.sun.syndication.io.FeedException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ArticleScheduler {
    private static final String[] RESOURCES_LIST = {
            "https://www.pravda.com.ua/rss/",
            "http://rss.cnn.com/rss/cnn_topstories.rss",
            "https://moxie.foxnews.com/google-publisher/world.xml",
            "https://rss.unian.net/site/news_ukr.rss"};
    // https://nv.ua/ukr/rss/all.xml - alternative

    private final ArticleParser articleParser;

    @Scheduled(initialDelay = 10000, fixedDelayString = "PT30M") // on start and every 30 minutes
    public void updateArticlesPeriodically() {
        try {
            updateArticlesFromResources();
        } catch (FeedException | IOException | ParseException e) {
            System.err.println("Failed to update articles: " + e.getMessage());
        }
    }

    private void updateArticlesFromResources() throws FeedException, IOException, ParseException {
        for (String resource : RESOURCES_LIST) {
            articleParser.parseArticle(resource);
            System.out.println("Update articles from: " + resource);
        }
    }
}
