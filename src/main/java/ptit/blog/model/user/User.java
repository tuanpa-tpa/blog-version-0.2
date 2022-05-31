package ptit.blog.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ptit.blog.model.Blog;
import ptit.blog.model.Comment;
import ptit.blog.util.CustomDateSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId", nullable = false)
    private Long userId;

    @Column(name = "Username", length = 30, nullable = false, unique = true)
    private String username;

    @Column(name = "Password", length = 100, nullable = false)
    private String password;

    @Column(name = "Email", length = 40, nullable = false, unique = true)
    private String email;

    @Column(name = "Name", columnDefinition = "VARCHAR(50) CHARACTER SET utf8")
    private String name;

    @Lob
    @Column(name = "Avatar")
    private String avatar;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @Column(name = "Verification_code", updatable = false)
    private String verificationCode;

    @Column(name = "Reset_password_code")
    private String resetPasswordCode;


    @ManyToMany(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @JoinTable(name = "tbl_user_role", joinColumns = {
            @JoinColumn(name = "UserId", referencedColumnName = "UserId")},
            inverseJoinColumns = {
                    @JoinColumn(name = "RoleId", referencedColumnName = "RoleId")})
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<Blog> blogs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<Comment> comments;
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private Set<Comment> comments;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "CreatedAt", nullable = false)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "UpdatedAt")
    private Date updatedAt;

}
