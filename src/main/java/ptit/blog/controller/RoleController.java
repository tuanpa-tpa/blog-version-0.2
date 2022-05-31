package ptit.blog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.role.ChangeRoleReq;
import ptit.blog.model.CustomUserPrincipal;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponseStatus;
import ptit.blog.service.RoleService;
import ptit.blog.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(path = "/role")
@Slf4j
@Api(tags = "IP")
public class RoleController {
    private final UserService userService;
    private final RoleService roleService;

    @ApiOperation(value = "Thêm role", response = ResponseEntity.class, authorizations = {@Authorization(value = "JWT")})
    @PostMapping(path = "/add")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> addRole(@RequestBody ChangeRoleReq req) {
//        ResponseObject<UserDto> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        UsernamePasswordAuthenticationToken user
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findByUsername(((CustomUserPrincipal) user.getPrincipal()).getUsername());
//        if (userDto.getSubscriberId() == null) {
//            return ResponseEntity.ok(new ResponseObject<>(false, ResponseStatus.SUBSCRIBER_NOT_SET));
//        } else {
//            ArrayList<GrantedAuthority> roles = new ArrayList<>(user.getAuthorities());
//            roles.removeIf(s -> !s.getAuthority().contains("ROLE_"));
            ResponseObject<UserDto> res = roleService.addRole(userDto.getUsername(), req);
            return ResponseEntity.ok(res);
//        }
    }

    @ApiOperation(value = "Xoá role", response = ResponseEntity.class, authorizations = {@Authorization(value = "JWT")})
    @DeleteMapping(path = "/delete")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> deleteRole(@RequestBody ChangeRoleReq req) {
        UsernamePasswordAuthenticationToken user
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findByUsername(((CustomUserPrincipal) user.getPrincipal()).getUsername());
//        if (userDto.getSubscriberId() == null) {
//            return ResponseEntity.ok(new ResponseObject<>(false, ResponseStatus.SUBSCRIBER_NOT_SET));
//        } else {
            ResponseObject<UserDto> res = roleService.deleteRole(userDto.getUsername(), req);
            return ResponseEntity.ok(res);
//        }
    }


    @ApiOperation(value = "Cập nhật role", response = ResponseEntity.class, authorizations = {@Authorization(value = "JWT")})
    @PutMapping(path = "/update")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> updateRole(@RequestBody ChangeRoleReq req) {
        UsernamePasswordAuthenticationToken user
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findByUsername(((CustomUserPrincipal) user.getPrincipal()).getUsername());
//        if (userDto.getSubscriberId() == null) {
//            return ResponseEntity.ok(new ResponseObject<>(false, ResponseStatus.SUBSCRIBER_NOT_SET));
//        } else {
            ResponseObject<UserDto> res = roleService.updateRole(userDto.getUsername(), req);
            return ResponseEntity.ok(res);
//        }
    }


    @ApiOperation(value = "Lấy ra tất cả role của username", response = ResponseEntity.class, authorizations = {@Authorization(value = "JWT")})
    @GetMapping(path = "/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getRolesUsername(@PathVariable String username) {
        ResponseObject<List<String>> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        List<String> roles = roleService.findRolesUsername(username);
        res.setData(roles);
        return ResponseEntity.ok(res);
    }

}
