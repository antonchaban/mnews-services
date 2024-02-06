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
        U_S_NEWS,
        COMEDY,
        PARENTING,
        WORLD_NEWS,
        CULTURE_AND_ARTS,
        TECH,
        SPORTS,
        ENTERTAINMENT,
        POLITICS,
        WEIRD_NEWS,
        ENVIRONMENT,
        EDUCATION,
        CRIME,
        SCIENCE,
        WELLNESS,
        BUSINESS,
        STYLE_AND_BEAUTY,
        FOOD_AND_DRINK,
        MEDIA,
        QUEER_VOICES,
        HOME_AND_LIVING,
        WOMEN,
        BLACK_VOICES,
        TRAVEL,
        MONEY,
        RELIGION,
        LATINO_VOICES,
        IMPACT,
        WEDDINGS,
        COLLEGE,
        PARENTS,
        ARTS_AND_CULTURE,
        STYLE,
        GREEN,
        TASTE,
        HEALTHY_LIVING,
        THE_WORLDPOST,
        GOOD_NEWS,
        WORLDPOST,
        FIFTY,
        ARTS,
        DIVORCE,
        UNKNOWN
    }

}
