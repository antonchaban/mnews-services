package a.chaban.fict.parsing.parsingservice.entities;


import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class Article {
    private String link;
    private Date articleDate;
    private String source;
    private String title_ua;
    private String description_ua;
    private String title_en;
    private String description_en;
    private Set<Category> categories = new HashSet<>();
    private Long userId;

    public enum Category {
        CATEGORY_WAR, CATEGORY_SPORT, CATEGORY_ECONOMY, CATEGORY_ENTERTAINMENT, CATEGORY_SCIENCE, CATEGORY_OTHER
    }
}
