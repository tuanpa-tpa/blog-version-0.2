package ptit.blog.service;

import ptit.blog.dto.entity.CommentDto;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.comment.CommentCreateReq;
import ptit.blog.response.ResponseObject;

import java.util.List;

public interface CommentService {
    ResponseObject<CommentDto> postComment(UserDto userDto, Long id, CommentCreateReq req);

    ResponseObject<List<CommentDto>> getAllComment(Long id);

    ResponseObject<Boolean> delete(Long id);
}
