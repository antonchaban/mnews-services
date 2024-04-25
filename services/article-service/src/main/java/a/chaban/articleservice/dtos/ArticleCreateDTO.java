package a.chaban.articleservice.dtos;


import a.chaban.articleservice.models.Category;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ArticleCreateDTO implements DTOEntity {
    private String title;
    private String description;
    private String link;
    private String source;
    private String category;
    private String language;
}
