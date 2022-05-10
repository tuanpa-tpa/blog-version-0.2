package ptit.blog.dto.request.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ApiModel(subTypes = {ChangeRoleReq.class})
public class ChangeRoleReq {
    @ApiModelProperty(value = "Tài khoản ",  name = "email", example = "nvhung6", required = true)
    private String username;
    @ApiModelProperty(value = "Role", name = "role", example = "[USER]", required = true)
    private List<String> roles;
}
