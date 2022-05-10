package ptit.blog.model.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ptit.blog.util.CustomDateSerializer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_permission")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@PersistenceContext
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PermissionId")
    private Long permissionId;

    @Column(name = "ActionCode", nullable = false, unique = true)
    private String actionCode;

    @Column(name = "ActionName", nullable = false, unique = true)
    private String actionName;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "CreatedAt", nullable = false)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "UpdatedAt")
    private Date updatedAt;

}
