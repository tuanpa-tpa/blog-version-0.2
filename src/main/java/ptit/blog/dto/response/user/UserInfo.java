package ptit.blog.dto.response.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(subTypes = {UserInfo.class})
public class UserInfo {
    @ApiModelProperty(value = "Email", name = "email", example = "hunga1k15tv1@gmail.com")
    private String email;
    @ApiModelProperty(value = "Tài khoản ",  name = "email", example = "nvhung6" )
    private String username;
    @ApiModelProperty(value = "Role", name = "Quyền", example = "Mô tả các quyền (authority)")
    private ArrayList<GrantedAuthority> role;
    @ApiModelProperty(value = "avatar", name = "Ảnh chân dung", example = "avatar")
    private String avatar;

}
