package ptit.blog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ptit.blog.dto.Mapper;
import ptit.blog.dto.entity.CommentDto;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.comment.CommentCreateReq;
import ptit.blog.dto.response.blog.BlogDetailsResp;
import ptit.blog.model.Blog;
import ptit.blog.model.Comment;
import ptit.blog.model.user.User;
import ptit.blog.repository.BlogRepo;
import ptit.blog.repository.CommentRepo;
import ptit.blog.repository.UserRepo;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponseStatus;
import ptit.blog.service.CommentService;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final BlogRepo blogRepo;
    private final UserRepo userRepo;


    @Override
    public ResponseObject<CommentDto> postComment(UserDto userDto, Long id, CommentCreateReq req) {
        ResponseObject<CommentDto> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        Blog blog = blogRepo.findById(id).orElseThrow(() -> new RuntimeException("not found blog"));
        User user = userRepo.findByUsername(userDto.getUsername());
        Comment comment = commentRepo.save(
                Comment.builder()
//                .blog(blog)
                .user(user)
                .content(req.getComment())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build()
                );
        Set<Comment> comments = blog.getComments();
        comments.add(comment);
        blog.setComments(comments);
        blogRepo.save(blog);
        res.setData(Mapper.responseCommentDtoFromModel(comment));
        return res;
    }

    @Override
    public ResponseObject<List<CommentDto>> getAllComment(Long id) {
        ResponseObject<List<CommentDto>> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        Blog blog = blogRepo.findById(id).orElseThrow(() -> new RuntimeException("not found blog"));
        Set<Comment> comments = blog.getComments();
        res.setData(comments.stream().map(Mapper::responseCommentDtoFromModel).collect(Collectors.toList()));
        return res;
    }

    @Override
    public ResponseObject<Boolean> delete(Long id) {
        ResponseObject<Boolean> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        commentRepo.deleteById(id);
        res.setData(true);
        return res;
    }
}
