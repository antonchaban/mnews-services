package a.chaban.articleservice.config;


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
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        return ResponseEntity.ok(articleService.save(article));
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> viewArticles(@RequestParam(name = "searchCategory", defaultValue = "") String searchCategory,
                               @RequestParam(name = "searchWord", defaultValue = "") String searchWord,
                               @CookieValue(name = "language", defaultValue = "en") String language,
                               @RequestParam(name = "searchSource", defaultValue = "") String searchSource,
                               @RequestParam(name = "newsDate", defaultValue = "") String newsDate) {
        return ResponseEntity.ok(articleService.listArticles(searchWord, searchSource, language, newsDate, searchCategory));
    }
}
