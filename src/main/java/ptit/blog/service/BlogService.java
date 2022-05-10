package ptit.blog.service;

import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.response.ResponseObject;

import java.util.List;

public interface BlogService {
    ResponseObject<List<BlogListDto>> getList();
}
