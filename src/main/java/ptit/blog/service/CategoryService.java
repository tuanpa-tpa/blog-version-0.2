package ptit.blog.service;

import ptit.blog.dto.response.category.CategoryListDto;
import ptit.blog.response.ResponseObject;

import java.util.List;

public interface CategoryService {
    ResponseObject<List<CategoryListDto>> getList();
}
