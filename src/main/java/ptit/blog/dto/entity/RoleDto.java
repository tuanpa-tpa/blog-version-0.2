package ptit.blog.dto.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ptit.blog.model.user.Permission;
import ptit.blog.util.CustomDateSerializer;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
    private Long roleId;
    private String roleName;
    private Set<Permission> permissions;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdAt;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updatedAt;
}
