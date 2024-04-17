package a.chaban.articleservice.services;

import a.chaban.articleservice.dtos.DTOEntity;
import a.chaban.articleservice.models.Article;
import a.chaban.articleservice.repositories.ArticleRepo;
import a.chaban.articleservice.repositories.UserRepo;
import a.chaban.articleservice.utils.DtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepo articleRepo;
    private final UserRepo userRepo;


    @Override
    public Article findById(long artId) {
        return articleRepo.findById(artId).orElse(null);
    }

    public Article save(DTOEntity article) { // todo check why date not saved https://prnt.sc/V8FvVq3UTmxS
        var savedArticle = (Article) new DtoUtils().convertToEntity(new Article(), article);
        if (savedArticle.getArticleDate() == null) savedArticle.setArticleDate(Date.from(Instant.now()));
        return articleRepo.save(savedArticle);
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
}
