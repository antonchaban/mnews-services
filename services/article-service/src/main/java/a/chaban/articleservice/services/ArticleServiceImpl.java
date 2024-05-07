package a.chaban.articleservice.services;

import a.chaban.articleservice.dtos.ArticleCreateDTO;
import a.chaban.articleservice.dtos.ArticleEditDTO;
import a.chaban.articleservice.dtos.ArticleSendDTO;
import a.chaban.articleservice.models.Article;
import a.chaban.articleservice.models.Category;
import a.chaban.articleservice.repositories.ArticleRepo;
import a.chaban.articleservice.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepo articleRepo;
    private final UserRepo userRepo;

    private final RabbitMQArticleProducer rabbitMQArticleProducer;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQArticleProducer.class);
    @Override
    public Article findById(long artId) {
        return articleRepo.findById(artId).orElse(null);
    }

    public Article createArticle(ArticleCreateDTO article, Long userId) {
        var newArticle = new Article();
        convertFromDTO(newArticle, article.getLanguage(), article.getTitle(), article.getDescription(),
                article.getLink(), article.getSource(), article.getCategory());
        newArticle.setArticleDate(Date.from(Instant.now()));
        newArticle.setUser(userRepo.findById(userId).orElse(null));
        newArticle = articleRepo.save(newArticle);
        LOGGER.info(String.format("New article in createArticle() -> %s", newArticle.toString()));
        sendToTranslate(newArticle, article.getLanguage());
        return newArticle;
    }

    private void sendToTranslate(Article article, String language) {
        var articleToSend = new ArticleSendDTO();
        articleToSend.setArticle(article);
        articleToSend.setLanguage(language);
        rabbitMQArticleProducer.sendArticleEntityToTranslate(articleToSend);
    }

    private void convertFromDTO(Article newArticle, String language, String title, String description,
                                String link, String source, String category) {

        switch (language) {
            case "en":
                newArticle.setTitle_en(title);
                newArticle.setDescription_en(description);

//                articleToSend.setLanguage("en");
//                rabbitMQArticleProducer.sendArticleEntityToTranslate(articleToSend);
                // todo auto translate
                break;
            case "ua":
                newArticle.setTitle_ua(title);
                newArticle.setDescription_ua(description);
//                articleToSend.setArticle(newArticle);
//                articleToSend.setLanguage("ua");
//                rabbitMQArticleProducer.sendArticleEntityToTranslate(articleToSend);
                // todo auto translate
                break;
        }
        newArticle.setLink(link);
        newArticle.setSource(source);
        try {
            newArticle.getCategories().clear();
            newArticle.getCategories().add(Category.valueOf(category));
        } catch (NullPointerException e) {
            System.out.println("Category is null, setting other value");
            newArticle.getCategories().clear();
            newArticle.getCategories().add(Category.GEOPOLITICS); // TODO change to unknown
        }
    }

    public void deleteById(long artId) {
        articleRepo.deleteById(artId);
    }

    public void delete(Article article) {
        articleRepo.delete(article);
    }

    public List<Article> findAll() {
        return articleRepo.findAll();
    }

    public List<Article> findAllByUserId(long userId) {
        return articleRepo.findAllByUser(userRepo.findById(userId).orElse(null));
    }

    public Article updateArticle(ArticleEditDTO articleEditDTO) {
        var article = articleRepo.findById(articleEditDTO.getId()).orElse(null);
        if (article == null) return null;
        convertFromDTO(article, articleEditDTO.getLanguage(), articleEditDTO.getTitle(),
                articleEditDTO.getDescription(), articleEditDTO.getLink(), articleEditDTO.getSource(),
                articleEditDTO.getCategory());
        article = articleRepo.save(article);
        sendToTranslate(article, articleEditDTO.getLanguage());
        return article;
    }
}
