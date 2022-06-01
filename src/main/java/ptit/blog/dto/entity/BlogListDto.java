package ptit.blog.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ptit.blog.model.Category;

import java.util.Date;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BlogListDto {
    private Long id;
    private Long img;
    private String title;
    private String avatar;
    private String username;
    private String blogText;
    private Long comment;
    private Set<Category> tags;
    private Date blogPosted;
}
