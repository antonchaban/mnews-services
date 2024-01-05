package a.chaban.articleservice.models;


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
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    private String link;

    private Date articleDate;

    private String source;

    private String title_ua;

    @Column(length = 2048)
    private String description_ua;

    private String title_en;

    @Column(length = 2048)
    private String description_en;

    @ToString.Exclude
    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "category"
            , joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Category> categories = new HashSet<>();

//    @ToString.Exclude
//    @ManyToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn
//    private Customer customer;


    @Transient
    @ToString.Exclude
    private Long customerId;
}
