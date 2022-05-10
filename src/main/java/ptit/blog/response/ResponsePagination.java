package ptit.blog.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponsePagination<T> {
    private long totalItems;
    private long size;
    private T data;
    private int totalPages;
    private int currentPage;
}
