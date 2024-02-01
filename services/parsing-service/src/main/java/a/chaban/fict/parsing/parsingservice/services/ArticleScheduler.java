package a.chaban.fict.parsing.parsingservice.services;

import com.sun.syndication.io.FeedException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ArticleScheduler {
    private final String[] RESOURCES_LIST = {
            "https://www.pravda.com.ua/rss/",
            "http://rss.cnn.com/rss/cnn_topstories.rss",
            "https://moxie.foxnews.com/google-publisher/world.xml",
            "https://rss.unian.net/site/news_ukr.rss"};
    // https://nv.ua/ukr/rss/all.xml - alternative
    private final ArticleParser articleParser;

    @Scheduled(initialDelay = 10000, fixedDelayString = "PT30M") // on start and every 30 minutes todo send this to other service
    public void updateArticles() throws FeedException, IOException, ParseException {
        for (String resource : RESOURCES_LIST) {
            articleParser.parseArticle(resource);
            System.out.println("Update articles from: " + resource);
        }
    }
}
