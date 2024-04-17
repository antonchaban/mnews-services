package a.chaban.articleservice.controllers;


import a.chaban.articleservice.dtos.ArticleCreateDTO;
import a.chaban.articleservice.dtos.ArticleEditDTO;
import a.chaban.articleservice.models.Article;
import a.chaban.articleservice.services.ArticleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ArticleController {
    private final ArticleServiceImpl articleService;

    @GetMapping("articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("articles/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }

    @DeleteMapping("articles/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable long id) {
        articleService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("articles")
    public ResponseEntity<Article> createArticle(@RequestBody ArticleCreateDTO article) {
        return ResponseEntity.ok(articleService.save(article)); // todo not tested
    }

    @PutMapping("articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody ArticleEditDTO article) { // todo not tested
        var art = articleService.findById(id);
        art = articleService.save(article);
        return ResponseEntity.ok(art);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> viewUsersArticles
            (@RequestParam(name = "userId", defaultValue = "") Long userId) {
        return ResponseEntity.ok(articleService.findAllByUserId(userId));
    }
}
