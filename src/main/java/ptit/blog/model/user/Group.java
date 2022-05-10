package ptit.blog.model.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ptit.blog.util.CustomDateSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_group")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@PersistenceContext

public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GroupId")
    private Long groupId;

    @Column(name = "GroupName", nullable = false, unique = true)
    private String groupName;

    @ManyToMany(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "tbl_group_role", joinColumns = {
            @JoinColumn(name = "GroupId", referencedColumnName = "GroupId")},
            inverseJoinColumns = {
                    @JoinColumn(name = "RoleId", referencedColumnName = "RoleId")})
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "tbl_group_user", joinColumns = {
            @JoinColumn(name = "GroupId", referencedColumnName = "GroupId")},
            inverseJoinColumns = {
                    @JoinColumn(name = "UserId", referencedColumnName = "UserId")})
    private Set<User> users;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "CreatedAt", nullable = false)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "UpdatedAt")
    private Date updatedAt;

}
