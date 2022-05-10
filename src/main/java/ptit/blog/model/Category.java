package ptit.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ptit.blog.util.CustomDateSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tbl_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private Long categoryId;

    @Column(name = "CategoryName", columnDefinition = "TEXT")
    private String categoryName;

    @Column(name = "Icon", columnDefinition = "VARCHAR(50)")
    @JsonIgnore
    private String icon;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "tbl_blog_category",
            joinColumns = {@JoinColumn(name = "CategoryId", referencedColumnName = "CategoryId")},
            inverseJoinColumns = {@JoinColumn(name = "BlogId", referencedColumnName = "BlogId")})
    private Set<Blog> blogs;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "CreatedAt", nullable = false)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "UpdatedAt")
    private Date updatedAt;
}
