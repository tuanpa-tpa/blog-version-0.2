package ptit.blog.dto.response.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ptit.blog.model.Category;

import java.util.Date;
import java.util.Set;

/**
 * Created by pat on 5/28/2022 - 7:10 AM
 *
 * @author pat
 * @project blog-version-0.2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BlogCreateResp {
    private Long featuredImageId;
    private String blogTitle;
    private String avatar;
    private String username;
    private String blogText;
    private Set<Category> blogCategories;
    private Date postedAt;
}
