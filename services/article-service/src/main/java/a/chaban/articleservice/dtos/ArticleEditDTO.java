package a.chaban.articleservice.dtos;


import lombok.Data;

@Data
public class ArticleEditDTO implements DTOEntity {
    private Long id;
    private String title;
    private String description;
    private String link;
    private String source;
    private String category;
    private String language;
}
