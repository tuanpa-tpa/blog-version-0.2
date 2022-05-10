package ptit.blog.dto.jwt;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestOfCheckJwt {
    private String role;
    private String access_token;
}
