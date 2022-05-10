package ptit.blog.service;

import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.user.ChangePasswordReq;
import ptit.blog.dto.request.user.CreateReq;
import ptit.blog.dto.request.user.ResetPasswordReq;
import ptit.blog.dto.response.user.ResetPasswordResp;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponsePagination;

public interface UserService {
    ResponseObject<UserDto> register(CreateReq req);
//    ResponseObject<UserDto> uploadAvatar(UploadAvatarReq req);
    ResponseObject<UserDto> create(CreateReq req);

    UserDto findByUsername(String username);


    ResponseObject<String> changePassword(ChangePasswordReq req);

    ResponseObject<ResponsePagination<Object>> getListUser(UserDto userDto, int page, int size, String[] sort);

    ResponseObject<String> getResetPasswordCode(String email);

    ResponseObject<ResetPasswordResp> resetPassword(ResetPasswordReq req);

    boolean verify(String verificationCode);
}
