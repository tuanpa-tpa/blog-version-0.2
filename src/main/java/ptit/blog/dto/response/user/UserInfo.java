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
    private String email;
    private String username;
    private ArrayList<GrantedAuthority> role;
    private String avatar;
    private String name;
}
