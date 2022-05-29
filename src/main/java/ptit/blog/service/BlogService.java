package ptit.blog.service;

import ptit.blog.dto.request.blog.BlogPostReq;
import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.blog.SearchBlog;
import ptit.blog.dto.request.blog.UpdateBlog;
import ptit.blog.dto.response.blog.BlogCreateResp;
import ptit.blog.dto.response.blog.BlogDetailsResp;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponsePagination;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    ResponseObject<List<BlogListDto>> getList();

    ResponseObject<ResponsePagination<Object>> search(SearchBlog req);

    ResponseObject<BlogDetailsResp> getDetails(Long id);

    ResponseObject<Boolean> deleteBlog(Long id);

    ResponseObject<BlogDetailsResp> update(UpdateBlog req);

    ResponseObject<BlogCreateResp> postBlog(UserDto userDto, BlogPostReq req) throws IOException;
}
