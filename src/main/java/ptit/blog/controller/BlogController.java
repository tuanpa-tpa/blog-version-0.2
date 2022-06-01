package ptit.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.blog.BlogPostReq;
import ptit.blog.dto.request.blog.SearchBlog;
import ptit.blog.dto.request.blog.UpdateBlog;
import ptit.blog.dto.response.blog.BlogCreateResp;
import ptit.blog.dto.response.blog.BlogDetailsResp;
import ptit.blog.model.CustomUserPrincipal;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponsePagination;
import ptit.blog.service.BlogService;
import ptit.blog.service.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/blog")
public class BlogController {

    private final BlogService blogService;
    private final UserService userService;

    @PostMapping("/list")
    public ResponseEntity<?> getList(@RequestBody SearchBlog req) {
        log.info("Controller: blog list");
        ResponseObject<ResponsePagination<Object>> res = this.blogService.search(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/listtest")
    public ResponseEntity<?> getLista() {
        log.info("Controller: blog list");
        ResponseObject<List<BlogListDto>> res = this.blogService.getList();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER','SUPER_ADMIN')")
    public ResponseEntity<?> getDetails(@PathVariable Long id) {
        log.info("Controller: get details blog");
        ResponseObject<BlogDetailsResp> res = this.blogService.getDetails(id);
        return ResponseEntity.ok(res);
    }

    @PostMapping(path = "/post",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> postBlog(@ModelAttribute @RequestBody BlogPostReq req) throws IOException {
        UsernamePasswordAuthenticationToken user
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = this.userService.findByUsername(((CustomUserPrincipal) user.getPrincipal()).getUsername());
        ResponseObject<BlogCreateResp> res = this.blogService.postBlog(userDto, req);
        return ResponseEntity.ok(res);
    }

    @PostMapping(path = "/update",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> updateBlog(@ModelAttribute @RequestBody UpdateBlog req) {
        log.info("Controller: update blog");
        ResponseObject<BlogDetailsResp> res = this.blogService.update(req);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id) {
        log.info("Controller: delete blog");
        ResponseObject<Boolean> res = this.blogService.deleteBlog(id);
        return ResponseEntity.ok(res);
    }
}
