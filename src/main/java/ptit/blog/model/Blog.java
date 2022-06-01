package ptit.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ptit.blog.model.user.User;
import ptit.blog.util.CustomDateSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tbl_blog")
public class Blog {
    @Id
//    @GeneratedValue(generator = "UUID")
//    @Type(type="uuid-char")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BlogId")
    private Long blogId;

    @Column(name = "Title")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ImgId", referencedColumnName = "ImgId")
    private Image img;

    @Column(name = "Content", columnDefinition = "TEXT CHARACTER SET utf8")
    private String content;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Comment> comments;


    @ManyToMany(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @JoinTable(name = "tbl_blog_category", joinColumns = {
            @JoinColumn(name = "BlogId", referencedColumnName = "BlogId")},
            inverseJoinColumns = {
                    @JoinColumn(name = "CategoryId", referencedColumnName = "CategoryId")})
    private Set<Category> categories;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "CreatedAt", nullable = false)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "UpdatedAt")
    private Date updatedAt;
}
