package a.chaban.articleservice.dtos;


import a.chaban.articleservice.models.Category;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ArticleCreateDTO implements DTOEntity {
    private String link;
    private String source;
    private String title_ua;
    private String description_ua;
    private String title_en;
    private String description_en;
    private Set<Category> categories = new HashSet<>();
}
