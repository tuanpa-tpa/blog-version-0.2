package ptit.blog.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Created by pat on 5/25/2022 - 9:24 PM
 *
 * @author pat
 * @project blog-version-0.2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchUser {
    private Integer page;
    private Integer size;
    private String[] sort;
    private String contains;
    private Boolean isActive;
    private String fromDate;
    private String toDate;
}
