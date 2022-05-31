package ptit.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ptit.blog.dto.entity.CommentDto;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.blog.SearchBlog;
import ptit.blog.dto.request.comment.CommentCreateReq;
import ptit.blog.model.CustomUserPrincipal;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponsePagination;
import ptit.blog.service.BlogService;
import ptit.blog.service.CommentService;
import ptit.blog.service.UserService;

import java.util.List;

/**
 * Created by pat on 5/25/2022 - 10:58 PM
 *
 * @author pat
 * @project blog-version-0.2
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/post/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER', 'SUPER_ADMIN')")
    public ResponseEntity<?> postComment(@PathVariable Long id, @RequestBody CommentCreateReq req) {
        log.info("Controller: post comment");
        UsernamePasswordAuthenticationToken user
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = this.userService.findByUsername(((CustomUserPrincipal) user.getPrincipal()).getUsername());
        ResponseObject<CommentDto> res = this.commentService.postComment(userDto, id, req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/list/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'SUPER_ADMIN')")
    public ResponseEntity<?> getAllComment(@PathVariable Long id) {
        log.info("Controller: get All comment post");
        ResponseObject<List<CommentDto>> res = this.commentService.getAllComment(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'SUPER_ADMIN')")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        log.info("Controller: delete comment by id");
        ResponseObject<Boolean> res = this.commentService.delete(id);
        return ResponseEntity.ok(res);
    }

}
