package a.chaban.articleservice.dtos;


import lombok.Data;

@Data
public class ArticleCreateDTO implements DTOEntity {
    private String title;
    private String description;
    private String link;
    private String source;
    private String category;
    private String language;
}
