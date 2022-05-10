package ptit.blog.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
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
@PersistenceContext
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

//    @Lob
//    @Column(name = "Avatar")
//    private String avatar;

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

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<Group> groups;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "CreatedAt", nullable = false)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "UpdatedAt")
    private Date updatedAt;

}
