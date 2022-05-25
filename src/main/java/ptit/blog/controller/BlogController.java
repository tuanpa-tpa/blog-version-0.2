package ptit.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.dto.request.blog.SearchBlog;
import ptit.blog.dto.response.blog.BlogDetailsResp;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponsePagination;
import ptit.blog.service.BlogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/blog")
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getList(@RequestBody SearchBlog req) {
        log.info("Controller: blog list");
        ResponseObject<ResponsePagination<Object>> res = this.blogService.search(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/listtest")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getLista() {
        log.info("Controller: blog list");
        ResponseObject<List<BlogListDto>> res = this.blogService.getList();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> postBlog(@PathVariable Long id) {
        log.info("Controller: post blog");
        ResponseObject<BlogDetailsResp> res = this.blogService.getDetails(id);
        return ResponseEntity.ok(res);
    }
}
