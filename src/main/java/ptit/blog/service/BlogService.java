package ptit.blog.service;

import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.dto.request.blog.SearchBlog;
import ptit.blog.dto.response.blog.BlogDetailsResp;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponsePagination;

import java.util.List;

public interface BlogService {
    ResponseObject<List<BlogListDto>> getList();

    ResponseObject<ResponsePagination<Object>> search(SearchBlog req);

    ResponseObject<BlogDetailsResp> getDetails(Long id);
}
