package ptit.blog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.user.*;
import ptit.blog.dto.response.user.ResetPasswordResp;
import ptit.blog.dto.response.user.UserInfo;
import ptit.blog.model.CustomUserPrincipal;
import ptit.blog.repository.UserRepo;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponsePagination;
import ptit.blog.service.UserService;
import ptit.blog.response.ResponseStatus;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/user")
@Slf4j
@RequiredArgsConstructor
@Api(tags = "User")
public class UserController {
    private final UserService userService;
    private final UserRepo userRepo;

    @PostMapping(path = "/change-password")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordReq req) {
        UsernamePasswordAuthenticationToken user
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        log.info("Controller: Thay đổi mật khẩu tài khoản {}", user.getCredentials());
        UserDto userDto = this.userService.findByUsername(((CustomUserPrincipal) user.getPrincipal()).getUsername());
        log.info(userDto.getEmail());
        ResponseObject<String> res = this.userService.changePassword(req);
        return ResponseEntity.ok(res);
    }


    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody CreateReq req) {
        log.info("Controller: Thêm mới tài khoản");
        ResponseObject<UserDto> res = this.userService.register(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping(path = "/info")
    public ResponseEntity<?> getInfo() {
        ResponseObject<UserInfo> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        log.info("Controller: Thông tin tài khoản");
        UsernamePasswordAuthenticationToken user
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = this.userService.findByUsername(((CustomUserPrincipal) user.getPrincipal()).getUsername());
        ArrayList<GrantedAuthority> role = new ArrayList<>(user.getAuthorities());
        role.removeIf(s -> !s.getAuthority().contains("ROLE_"));

        UserInfo userInfo = UserInfo.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .role(role)
                .avatar(userDto.getAvatar())
                .name(userDto.getName())
                .build();
        res.setData(userInfo);
        return ResponseEntity.ok(res);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody UpdateUserReq req) {
        log.info("Controller: cập nhật user");
        UsernamePasswordAuthenticationToken user
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = this.userService.findByUsername(((CustomUserPrincipal) user.getPrincipal()).getUsername());
        ResponseObject<UserDto> res = this.userService.updateUser(req, userDto);
        return ResponseEntity.ok(res);
    }


//    @ApiOperation(value = "Thêm mới tài khoản", response= ResponseEntity.class,authorizations = { @Authorization(value="JWT")})
//    @PostMapping(path = "/upload/avatar",
//            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
//                    MediaType.APPLICATION_JSON_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<?> uploadAvatar(@Valid @ModelAttribute @RequestBody UploadAvatarReq req) {
//        log.info("Controller: Thêm mới tài khoản");
//        ResponseObject<UserDto> res = this.userService.uploadAvatar(req);
//        return ResponseEntity.ok(res);
//    }


    @GetMapping(path = "/verify")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> verify(@RequestParam("code") String verificationCode) {
        log.info("Controller: Xác thực tài khoản người dùng");
        boolean verified = userService.verify(verificationCode);
        if (verified) {
            log.info("Verify successfully !");
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:4200/pages/authentication/login")).build();
        }
        log.info("Verification failed !");
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:4200/pages/miscellaneous/error")).build();
    }

    @PostMapping("/reset_password_code/{email}")
    public ResponseEntity<?> getResetPasswordCode(@PathVariable String email) {
        log.info("Controller: Lấy code rest password");
        ResponseObject<String> res = userService.getResetPasswordCode(email);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> verifyResetPassword(@RequestBody ResetPasswordReq req) {
        log.info("Controller: verify reset password");
        ResponseObject<ResetPasswordResp> res = userService.resetPassword(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> getAllUser(@RequestBody SearchUser searchUser) {
        log.info("Controller: get All User");
        ResponseObject<ResponsePagination<Object>> res = userService.search(searchUser);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        log.info("Controller: delete User");
        log.info(id);
        ResponseObject<Boolean> res = userService.delete(Long.parseLong(id));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> finUser(@PathVariable Long id) {
        log.info("Controller: find User");
        ResponseObject<UserDto> res = userService.findUser(id);
        return ResponseEntity.ok(res);
    }
}
