package ptit.blog.dto.response.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ptit.blog.model.Category;
import ptit.blog.model.Comment;

import java.util.Date;
import java.util.Set;

/**
 * Created by pat on 5/25/2022 - 10:40 PM
 *
 * @author pat
 * @project blog-version-0.2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BlogDetailsResp {
    private Long img;
    private String title;
    private String avatar;
    private String username;
    private String content;
    private Long comments;
    private Long bookmarked;
    private Set<Category> tags;
    private Date postedAt;
}
