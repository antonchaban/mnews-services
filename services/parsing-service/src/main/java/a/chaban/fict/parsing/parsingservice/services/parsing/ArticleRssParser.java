package a.chaban.fict.parsing.parsingservice.services.parsing;

import a.chaban.fict.parsing.parsingservice.entities.Article;
import a.chaban.fict.parsing.parsingservice.services.Parser;
import a.chaban.fict.parsing.parsingservice.services.translation.TranslateAPIParser;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// todo IMPORTANT find out why there is duplicates of articles and fix it
// TODO IMPORTANT handle exception when article parsing fails and it adds empty fields to the database
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleRssParser implements Parser<ArrayList<Article>> {
    private final URLConnector urlConnector;

    private final TranslateAPIParser translateAPIParser;

    @Override
    public ArrayList<Article> doParse(String resource) throws IOException, FeedException, ParseException {
        HttpURLConnection connection = urlConnector.setConnection(resource);
        ArrayList<Article> list = new ArrayList<>();
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(connection));
            List entries = feed.getEntries();
            Iterator itEntries = entries.iterator();

            while (itEntries.hasNext()) {
                SyndEntry entry = (SyndEntry) itEntries.next();
                Article article = new Article();
                String language = translateAPIParser.detectLanguage(entry.getTitle());
                switch (language) {
                    case "uk" -> setUkArticle(article, entry);
                    case "en" -> setEnArticle(article, entry);
                }
                list.add(article);
            }
        } finally {
            urlConnector.endConnection(connection);
        }
        return list;
    }


    private void setUkArticle(Article article, SyndEntry entry) {
        try {
            article.setTitle_ua(entry.getTitle());
        } catch (NullPointerException e) {
            article.setTitle_ua("Помилка заголовку");
        }
        article.setLink(entry.getLink());
        if (entry.getAuthor() != null) article.setSource(entry.getAuthor());
        try {
            if (!entry.getDescription().getValue().isEmpty())
                article.setDescription_ua(entry.getDescription().getValue());
            else
                article.setDescription_ua("Немає опису, перегляньте оригінальну статтю для отримання додаткової інформації");
        } catch (NullPointerException e) {
            article.setDescription_ua("Немає опису, перегляньте оригінальну статтю для отримання додаткової інформації");
        }
        if (entry.getPublishedDate() != null) article.setArticleDate(entry.getPublishedDate());
        else article.setArticleDate(Date.from(Instant.now()));
    }

    private void setEnArticle(Article article, SyndEntry entry) {
        try {
            article.setTitle_en(entry.getTitle());
        } catch (NullPointerException e) {
            article.setTitle_en("Tittle Error");
        }
        article.setLink(entry.getLink());
        if (entry.getAuthor() != null) {
            article.setSource(entry.getAuthor());
        }
        try {
            if (!entry.getDescription().getValue().isEmpty()) {
                article.setDescription_en(entry.getDescription().getValue());
            } else {
                article.setDescription_en("No description, view original article for additional information");
            }
        } catch (NullPointerException e) {
            article.setDescription_en("No description, view original article for additional information");
        }
        if (entry.getPublishedDate() != null) article.setArticleDate(entry.getPublishedDate());
        else article.setArticleDate(Date.from(Instant.now()));
    }
}
