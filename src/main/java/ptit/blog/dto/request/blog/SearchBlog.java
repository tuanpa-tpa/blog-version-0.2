package ptit.blog.dto.request.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by pat on 5/25/2022 - 9:43 PM
 *
 * @author pat
 * @project blog-version-0.2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchBlog {
    private Integer page;
    private Integer size;
    private String[] sort;
    private String contains;
    private String fromDate;
    private String toDate;
}
