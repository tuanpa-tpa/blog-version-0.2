package ptit.blog.service;

import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.role.ChangeRoleReq;
import ptit.blog.response.ResponseObject;

import java.util.List;

public interface RoleService {
    ResponseObject<UserDto> addRole(String username, ChangeRoleReq req);
    ResponseObject<UserDto> deleteRole(String username, ChangeRoleReq req);
    ResponseObject<UserDto> updateRole(String username, ChangeRoleReq req);

    List<String> findRolesUsername(String username);
}
