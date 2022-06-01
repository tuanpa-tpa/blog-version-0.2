package ptit.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.dto.response.category.CategoryListDto;
import ptit.blog.repository.CategoryRepo;
import ptit.blog.response.ResponseObject;
import ptit.blog.service.CategoryService;

import java.util.List;

/**
 * Created by pat on 5/28/2022 - 8:14 AM
 *
 * @author pat
 * @project blog-version-0.2
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN','USER')")
    public ResponseEntity<?> getList() {
        log.info("Controller: category list");
        ResponseObject<List<CategoryListDto>> res = this.categoryService.getList();
        return ResponseEntity.ok(res);
    }
}
