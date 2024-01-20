package a.chaban.articleservice.services;

import a.chaban.articleservice.dtos.ArticleCreateDTO;
import a.chaban.articleservice.dtos.DTOEntity;
import a.chaban.articleservice.models.Article;
import a.chaban.articleservice.repositories.ArticleRepo;
import a.chaban.articleservice.repositories.UserRepo;
import a.chaban.articleservice.utils.DtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Article save(DTOEntity article) {
        return articleRepo.save((Article) new DtoUtils().convertToEntity(new Article(), article));
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

    public List<Article> listArticles(String searchWord, String searchSource, String language, String newsDate,
                                      String searchCategory) // todo implement
    {
        return articleRepo.findAll();
    }
}
