package ptit.blog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ptit.blog.dto.Mapper;
import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.dto.response.category.CategoryListDto;
import ptit.blog.model.Blog;
import ptit.blog.model.Category;
import ptit.blog.repository.CategoryRepo;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponseStatus;
import ptit.blog.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    @Override
    public ResponseObject<List<CategoryListDto>> getList() {
        ResponseObject<List<CategoryListDto>> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        List<Category> categoryListDtos = categoryRepo.findAll();
        List<CategoryListDto> result = categoryListDtos.stream()
                .map(Mapper::responseCategoryListFromModel).collect(Collectors.toList());
        res.setData(result);
        return res;
    }
}
