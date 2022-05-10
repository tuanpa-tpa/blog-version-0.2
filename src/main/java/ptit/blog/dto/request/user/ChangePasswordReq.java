package ptit.blog.dto.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(subTypes = {ChangePasswordReq.class})
public class ChangePasswordReq {
    //    private String username;
    @ApiModelProperty(value = "Mật khẩu cũ", name = "oldPassword", example = "cmcPass97", required = true)
    private String oldPassword;
    @ApiModelProperty(value = "Mật khẩu mới", name = "newPassword", example = "abcd1234")
    private String newPassword;
//    private String email;
//    private User.Role role;
}
