package ptit.blog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.role.ChangeRoleReq;
import ptit.blog.exception.role.*;
import ptit.blog.model.user.Role;
import ptit.blog.model.user.User;
import ptit.blog.repository.RoleRepo;
import ptit.blog.repository.UserRepo;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponseStatus;
import ptit.blog.service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final RoleRepo roleRepo;

    @Override
    public ResponseObject<UserDto> addRole(String username, ChangeRoleReq req) {
        ResponseObject<UserDto> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        req.setRoles(req.getRoles().stream()
                .filter(s -> s.contains("ROLE_"))
                .map(s -> s.substring("ROLE_".length()))
                .collect(Collectors.toList()));
        if (req.getRoles().isEmpty() || req.getUsername().isEmpty() || username.isEmpty()) {
            throw new RoleReqEmptyException();
        }
        List<String> roles = findRolesUsername(username);
        if (roles.isEmpty()) {
            throw new RoleReqEmptyException();
        }
        User tmp = null;
        if (roles.contains("SUPER_ADMIN")) {
            tmp = editRoleSA(req, 1);
        } else if (roles.contains("ADMIN")) {
            tmp = editRoleA(req, 1);
        }
        res.setData(this.modelMapper.map(tmp, UserDto.class));
        if (tmp == null) {
            throw new ErrorEditRoleException();
        }
        return res;
    }

    @Override
    public ResponseObject<UserDto> deleteRole(String username, ChangeRoleReq req) {
        ResponseObject<UserDto> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        req.setRoles(req.getRoles().stream()
                .filter(s -> s.contains("ROLE_"))
                .map(s -> s.substring("ROLE_".length()))
                .collect(Collectors.toList()));
        if (req.getRoles().isEmpty() || req.getUsername().isEmpty() || username.isEmpty()) {
            throw new RoleReqEmptyException();
        }
        List<String> roles = findRolesUsername(username);
        if (roles.isEmpty()) {
            throw new RoleReqEmptyException();
        }
        User tmp = null;
        if (roles.contains("SUPER_ADMIN")) {
            tmp = editRoleSA(req, 2);
        } else if (roles.contains("ADMIN")) {
            tmp = editRoleA(req, 2);
        }
        res.setData(this.modelMapper.map(tmp, UserDto.class));
        if (tmp == null) {
            throw new ErrorEditRoleException();
        }
        return res;
    }

    @Override
    public ResponseObject<UserDto> updateRole(String username, ChangeRoleReq req) {
        ResponseObject<UserDto> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        req.setRoles(req.getRoles().stream()
                .filter(s -> s.contains("ROLE_"))
                .map(s -> s.substring("ROLE_".length()))
                .collect(Collectors.toList()));
        if (req.getRoles().isEmpty() || req.getUsername().isEmpty() || username.isEmpty()) {
            throw new RoleReqEmptyException();
        }
        List<String> roles = findRolesUsername(username);
        if (roles.isEmpty()) {
            throw new RoleReqEmptyException();
        }
        User user = null;
        if (roles.contains("SUPER_ADMIN")) {
            user = editRoleSA(req, 3);
        } else if (roles.contains("ADMIN")) {
            user = editRoleA(req, 3);
        }
        res.setData(this.modelMapper.map(user, UserDto.class));
        return res;
    }

    @Override
    public List<String> findRolesUsername(String username) {
        User user = this.userRepo.findByUsername(username);
        if (user == null) {
            throw new RoleNotFoundUsernameException(username);
        }
        return user.getRoles().stream()
                .map(Role::getRoleName).collect(Collectors.toList());
    }

    private User editRoleSA(ChangeRoleReq req, int edit) {
        User user = this.userRepo.findByUsername(req.getUsername());
        if (user == null) {
            throw new RoleNotFoundUsernameException(req.getUsername());
        }
        try {
            if (edit == 1) { // add
                user.setRoles(add(user, req));
            } else if (edit == 2) { // delete
                user.setRoles(delete(user, req));
            } else {
                user.setRoles(update(user, req));
            }
            this.userRepo.saveAndFlush(user);
        } catch (DataAccessException e) {
            throw new RoleDataAccessException(e.getMessage());
        }
        return user;
    }

    private User editRoleA(ChangeRoleReq req, int edit) {
        User user = this.userRepo.findByUsername(req.getUsername());
        if (user == null) {
            throw new RoleNotFoundUsernameException(req.getUsername());
        }
        List<String> listRole = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        if (listRole.contains("SUPER_ADMIN")) {
            throw new RoleAuthorizationException(req.getUsername());
        }
        try {
            if (edit == 1) { // add
                user.setRoles(add(user, req));
            } else if (edit == 2) { // delete
                user.setRoles(delete(user, req));
            } else {
                user.setRoles(update(user, req));
            }
            this.userRepo.saveAndFlush(user);
        } catch (DataAccessException e) {
            throw new RoleDataAccessException(e.getMessage());
        }
        return user;
    }


    private Set<Role> update(User user, ChangeRoleReq req) {
        Set<Role> roles = new HashSet<>();
        req.getRoles().forEach(r -> {
            roles.add(this.roleRepo.findByRoleName(r));
        });
        return roles;
    }

    private Set<Role> delete(User user, ChangeRoleReq req) {
        boolean checkUser = false;
        Role roleCheckUser = this.roleRepo.findByRoleName("USER");
        Set<Role> roles = user.getRoles();
        if (roles.contains(roleCheckUser)) {
            checkUser = true;
        }
        req.getRoles().forEach(r -> {
            roles.remove(this.roleRepo.findByRoleName(r));
        });
        if (checkUser || roles.isEmpty()) {
            roles.add(roleCheckUser);
        }
        return roles;
    }

    private Set<Role> add(User user, ChangeRoleReq req) {
        Set<Role> roles = user.getRoles();
        req.getRoles().forEach(r -> {
            roles.add(this.roleRepo.findByRoleName(r));
        });
        return roles;
    }

}
