package ptit.blog.dto.request.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by pat on 5/27/2022 - 1:22 PM
 *
 * @author pat
 * @project blog-version-0.2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BlogPostReq {
    private MultipartFile img;
    private String title;
    private String content;
    private String[] categories;
}
