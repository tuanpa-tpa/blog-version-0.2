package ptit.blog.dto.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ApiModel(subTypes = {UploadAvatarReq.class})
public class UploadAvatarReq {
    @ApiModelProperty(value = "Ảnh đại diện",  name = "avatar", example = "1", required = true)
    private MultipartFile avatar;
}
