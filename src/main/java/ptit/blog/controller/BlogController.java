package ptit.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.response.ResponseObject;
import ptit.blog.service.BlogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/blog")
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getList() {
        log.info("Controller: blog list");
        ResponseObject<List<BlogListDto>> res = this.blogService.getList();
        return ResponseEntity.ok(res);
    }
}
