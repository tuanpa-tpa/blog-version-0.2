package ptit.blog.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResetPasswordResp {
    private String email;
    private String password;
}
