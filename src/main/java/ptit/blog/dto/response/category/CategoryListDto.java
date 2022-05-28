package ptit.blog.dto.response.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by pat on 5/28/2022 - 8:17 AM
 *
 * @author pat
 * @project blog-version-0.2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryListDto {
    private String category;
    private String icon;
}
