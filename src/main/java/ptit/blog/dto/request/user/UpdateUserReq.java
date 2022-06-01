package ptit.blog.dto.request.user;

import lombok.*;

/**
 * Created by pat on 6/1/2022 - 4:32 PM
 *
 * @author pat
 * @project blog-version-0.2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateUserReq {
    private String name;
}
