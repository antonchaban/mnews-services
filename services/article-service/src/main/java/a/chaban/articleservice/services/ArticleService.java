package a.chaban.articleservice.services;


import a.chaban.articleservice.models.Article;

public interface ArticleService {
    Article findById(long artId);
}
