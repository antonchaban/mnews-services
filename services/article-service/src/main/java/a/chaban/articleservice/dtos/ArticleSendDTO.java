package a.chaban.articleservice.dtos;

import a.chaban.articleservice.models.Article;
import lombok.Data;

@Data
public class ArticleSendDTO {
    private Article article;
    private String language;
}