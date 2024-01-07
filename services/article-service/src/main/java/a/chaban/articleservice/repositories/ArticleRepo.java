package a.chaban.articleservice.repositories;

import a.chaban.articleservice.models.Article;
import a.chaban.articleservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    List<Article> findAllByUser(User user);
}
