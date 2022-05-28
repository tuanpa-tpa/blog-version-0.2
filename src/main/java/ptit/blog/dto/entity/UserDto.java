package ptit.blog.dto.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ptit.blog.model.user.Group;
import ptit.blog.util.CustomDateSerializer;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDto {
    private Long userId;
    private String email;
    private String username;
//    private Set<Group> groups;
    private Boolean isActive;
    private String avatar;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdAt;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updatedAt;
}
