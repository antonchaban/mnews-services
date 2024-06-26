package a.chaban.articleservice.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    private Date articleDate;

    private String source;

    @Column(length = 2048)
    private String title_ua;

    @Column(length = 16384)
    private String description_ua;

    @Column(length = 2048)
    private String title_en;

    @Column(length = 16384)
    private String description_en;

    @ToString.Exclude
    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "category"
            , joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Category> categories = new HashSet<>();


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    @JsonIgnoreProperties("articles")
    @ToString.Exclude
    private User user;


    @Transient
    private Long userId;
}
