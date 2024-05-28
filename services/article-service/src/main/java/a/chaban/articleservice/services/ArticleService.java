package a.chaban.articleservice.services;


import a.chaban.articleservice.dtos.ArticleCreateDTO;
import a.chaban.articleservice.dtos.ArticleEditDTO;
import a.chaban.articleservice.models.Article;

import java.util.List;

public interface ArticleService {
    Article findById(long artId);
    Article createArticle(ArticleCreateDTO article, Long userId);
    void deleteById(long artId);
    List<Article> findAll();
    List<Article> findAllByUserId(long userId);
    Article updateArticle(ArticleEditDTO articleEditDTO);
}
