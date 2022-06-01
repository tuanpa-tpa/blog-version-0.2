package ptit.blog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ptit.blog.dto.EmailRegistrationDto;
import ptit.blog.dto.Mapper;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.user.*;
import ptit.blog.dto.response.user.CreateUserResp;
import ptit.blog.dto.response.user.ResetPasswordResp;
import ptit.blog.exception.user.UserBadReqException;
import ptit.blog.exception.user.UserDataAccessException;
import ptit.blog.exception.user.UserException;
import ptit.blog.exception.user.UserNotFoundUsernameException;
import ptit.blog.model.user.Role;
import ptit.blog.model.user.User;
import ptit.blog.repository.RoleRepo;
import ptit.blog.repository.UserRepo;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponsePagination;
import ptit.blog.response.ResponseStatus;
import ptit.blog.service.SendEmailService;
import ptit.blog.service.UserService;
import ptit.blog.utilservice.PaginationCustom;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepo roleRepo;
    private final PaginationCustom paginationCustom;
    private final SendEmailService sendEmailService;
    @Value("${spring.mail.username}")
    private String emailFrom;
//    @Value("${spring.mail.username}")
//    private String emailFrom;
//    private final SendEmailService sendEmailService;
    @Override
    public ResponseObject<UserDto> register(CreateReq req) {
        if (!req.getRole().equals("USER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Don't have permission to register User with level higher USER");
        }
        return this.create(req);
    }

//    @Override
//    public ResponseObject<UserDto> uploadAvatar(UploadAvatarReq req) {
//        ResponseObject<UserDto> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
//    try {
//        byte[] avatar = req.getAvatar().getBytes();
//
//        String username = getCurrentUser();
//        User user = this.userRepo.findByUsername(username);
//        if (user == null) {
//            throw new UserNotFoundUsernameException(username);
//        }
//        user.setAvatar(Base64.getEncoder().encodeToString(avatar));
//        user = this.userRepo.saveAndFlush(user);
//        res.setData(MapperIp.responseUserDtoFromModel(user));
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//    return res;
//    }

    @Override
    public ResponseObject<UserDto> create(CreateReq req) {
        ResponseObject<UserDto> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepo.findByRoleName(req.getRole()));
//        log.info(this.roleRepo.findByRoleName(req.getRole()).getRoleName());
//        userRepo.deleteById(7);
        if (userRepo.findByEmail(req.getEmail()) != null || userRepo.findByUsername(req.getUsername()) != null) {
            throw new UserBadReqException("username or email is duplicate");
        }
        try {
            String verificationCode = RandomString.make(64);
            log.info(verificationCode);
            User user = this.userRepo.save(
                    User.builder()
                            .username(req.getUsername())
                            .name(req.getName())
                            .password(passwordEncoder.encode(req.getPassword()))
                            .email(req.getEmail())
                            .createdAt(new Date())
                            .updatedAt(new Date())
                            .avatar("assets/images/portrait/small/avatar-s-1.jpg")
                            .roles(roles)
                            .verificationCode(verificationCode)
                            .isActive(false)
                            .build()
            );
            res.setData(Mapper.responseUserDtoFromModel(user));

            //Send mail
            EmailRegistrationDto emailRequest = EmailRegistrationDto.builder()
                    .from(emailFrom)
                    .to(user.getEmail())
                    .subject("Vui lòng xác thực tài khoản !").build();
            EmailRegistrationDto.ContentEmail contentEmail = EmailRegistrationDto.ContentEmail.builder()
                    .name(user.getUsername())
                    .email(user.getEmail())
                    .role(StringUtils.join(roles.stream().map(Role::getRoleName).collect(Collectors.toList()), ", "))
                    .verificationCode(verificationCode)
                    .build();

            long start = System.currentTimeMillis();

            sendEmailService.sendEmailSelfRegistration(emailRequest, contentEmail, user.getUserId());

            log.info("User:: elapsed time: " + (System.currentTimeMillis() - start));
        } catch (DataAccessException ex) {
            throw new UserDataAccessException(ex.getMessage());
        }
        return res;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = this.userRepo.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundUsernameException(username);
        }
        return Mapper.responseUserDtoFromModel(user);
    }


    @Override
    public ResponseObject<String> changePassword(ChangePasswordReq req) {
        ResponseObject<String> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        log.info("Service changePassword {}", getCurrentUser());
        try {
            String username = getCurrentUser();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, req.getOldPassword()));
            User user = this.userRepo.findByUsername(username);
            user.setPassword(passwordEncoder.encode(req.getNewPassword()));
            user = this.userRepo.saveAndFlush(user);
            res.setData("Success");
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
        return res;
    }

    //    @Override
//    public ResponseObject<ResponsePagination<Object>> getListUser(UserDto userDto, int page, int size, String[] sort) {
//        ResponseObject<ResponsePagination<Object>> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
//        try {
//            Pageable pagingSort = this.paginationCustom.createPaginationCustom(page, size, sort);
//            Page<User> pageUsers;
//            pageUsers = this.userRepo.findAll(pagingSort);
//            log.info(pageUsers.get().toString());
//            List<UserDto> organizations = pageUsers.stream().map(x -> modelMapper.map(x, UserDto.class)).collect(Collectors.toList());
//            ResponsePagination<Object> responsePagination = ResponsePagination.builder()
//                    .data(organizations)
//                    .currentPage(pageUsers.getNumber())
//                    .totalPages(pageUsers.getTotalPages())
//                    .totalItems(pageUsers.getTotalElements())
//                    .build();
//            res.setData(responsePagination);
//        } catch (Exception e) {
//            log.error(e.toString());
//            return new ResponseObject<>(false, ResponseStatus.UNHANDLED_ERROR, e.getMessage());
//        }
//        return res;
//    }

    @Override
    public ResponseObject<ResponsePagination<Object>> getListUser(UserDto userDto, int page, int size, String[] sort) {
        ResponseObject<ResponsePagination<Object>> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        try {
            Pageable pagingSort = this.paginationCustom.createPaginationCustom(page, size, sort);
            Page<User> pageUsers;
            pageUsers = this.userRepo.findAll(pagingSort);
            log.info(pageUsers.get().toString());
            List<CreateUserResp> users = pageUsers.stream().map(Mapper::responseUserFromModel).collect(Collectors.toList());
            ResponsePagination<Object> responsePagination = ResponsePagination.builder()
                    .data(users)
                    .currentPage(pageUsers.getNumber())
                    .totalPages(pageUsers.getTotalPages())
                    .totalItems(pageUsers.getTotalElements())
                    .build();
            res.setData(responsePagination);
        } catch (Exception e) {
            log.error(e.toString());
            throw new UserException(e.getMessage());
        }
        return res;
    }

    @Override
    public ResponseObject<String> getResetPasswordCode(String email) {
        ResponseObject<String> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        User user = this.userRepo.findByEmail(email);
        if (user == null) {
            throw new UserException("not found username");
        }
        Random rand = new Random();
        int numberRandom = rand.nextInt(99999999);
        String resetPasswordCode = String.format("%8d", numberRandom);
        user.setResetPasswordCode(resetPasswordCode);
        user = this.userRepo.save(user);
        res.setData(user.getResetPasswordCode());
        EmailRegistrationDto emailRequest = EmailRegistrationDto.builder()
                .from(emailFrom)
                .to(user.getEmail())
                .subject("Xác thực đặt lại mật khẩu !").build();
        EmailRegistrationDto.ContentEmailResetPassword contentEmail = EmailRegistrationDto.ContentEmailResetPassword.builder()
                .name(user.getUsername())
                .email(user.getEmail())
                .role(StringUtils.join(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()), ", "))
                .resetPasswordCode(user.getResetPasswordCode())
                .build();

        long start = System.currentTimeMillis();

        sendEmailService.sendMailResetPassword(emailRequest, contentEmail, user.getUserId());

        log.info("User:: elapsed time: " + (System.currentTimeMillis() - start));
        return res;
    }

    @Override
    public ResponseObject<ResetPasswordResp> resetPassword(ResetPasswordReq req) {
        ResponseObject<ResetPasswordResp> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        String code = req.getCode().trim();
        User user1 = this.userRepo.findByResetPasswordCode(code);
//        String code = req.getResetPasswordCode().trim();
//        User user = userRepo.findByEmail(req.getEmail());
        if (user1 == null) {
            throw new UserException("not reset password");
        }
//        log.info(user.getUsername());
        if (code.equals(user1.getResetPasswordCode())) {
            user1.setPassword(passwordEncoder.encode(req.getPassword()));
            user1.setResetPasswordCode(null);
            userRepo.saveAndFlush(user1);
        } else {
            throw new UserException("not reset password");
        }
        ResetPasswordResp result = ResetPasswordResp.builder()
                .email(user1.getEmail())
                .password(req.getPassword())
                .build();
        res.setData(result);
        return res;
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepo.findByVerificationCode(verificationCode);
        if (user == null || user.getIsActive()) {
            return false;
        } else {
            user.setIsActive(true);
            userRepo.saveAndFlush(user);
            return true;
        }
    }

    @Override
    public ResponseObject<ResponsePagination<Object>> search(SearchUser req) {
        ResponseObject<ResponsePagination<Object>> res = new ResponseObject<>(true,
                ResponseStatus.DO_SERVICE_SUCCESSFUL);
        // if page or size is null then set page = 0 and size = 20
        int page = (req.getPage() == null) ? 0 : req.getPage();
        int size = (req.getSize() == null) ? 20 : req.getSize();
        // get data from request and check if its data is null
        String contains = ((req.getContains() == null) || (req.getContains().equals(""))) ? null
                : req.getContains().trim();
        Boolean isActive = ((req.getIsActive() == null) || (req.getContains().equals(""))) ? null
                : req.getIsActive();
        // if there are more than one space between each word then replace with one
        // space only
        if (contains != null) {
            String[] words = contains.split("\\s+");
            contains = String.join(" ", words);
        }
        // if sort array is null then set defaut value is organizationId, asc
        String[] sort = ((req.getSort() == null) || (req.getSort().length == 0)) ? new String[] { "userId,asc" }
                : req.getSort();
        // Convert fromDate and toDate String to Date
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date fromDate = null, toDate = null;
        try {
            // Create Pagable
            Pageable pageable = this.paginationCustom.createPaginationCustom(page, size, sort);
            // set default value for fromDate and toDate
            if (req.getFromDate() != null && !req.getFromDate().equals(""))
                fromDate = sf.parse(req.getFromDate() + " 00:00:00");
            if (req.getToDate() != null && !req.getToDate().equals(""))
                toDate = sf.parse(req.getToDate() + " 23:59:59");
            // Get list staffModel from search method in repo
            Page<User> pageUser = null;
                pageUser = userRepo.search(contains, isActive, fromDate, toDate, pageable);
            res.setData(ResponsePagination.builder()
                    .data(pageUser.stream().map(Mapper::responseUserDtoFromModel).collect(Collectors.toList()))
                    .size(pageUser.getSize())
                    .currentPage(pageUser.getNumber())
                    .totalPages(pageUser.getTotalPages())
                    .totalItems(pageUser.getTotalElements())
                    .build());
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
        return res;
    }

    @Override
    public ResponseObject<Boolean> delete(Long id) {
        ResponseObject<Boolean> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        try {
            res.setData(false);
            userRepo.deleteById(id);
            res.setData(true);
        } catch (Exception e) {
            throw new UserException("delete error");
        }
        return res;
    }

    @Override
    public ResponseObject<UserDto> findUser(Long id) {
        ResponseObject<UserDto> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        try {
            User user = userRepo.findById(id).orElseThrow(() -> new UserException("not found user"));
            res.setData(Mapper.responseUserDtoFromModel(user));
        } catch (Exception e) {
            throw new UserException("not found user");
        }
        return res;
    }

    @Override
    public ResponseObject<UserDto> updateUser(UpdateUserReq req, UserDto userDto) {
        ResponseObject<UserDto> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        try {
            User user = userRepo.findByUsername(userDto.getUsername());
            user.setName(req.getName());
            userRepo.save(user);
            res.setData(Mapper.responseUserDtoFromModel(user));
        } catch (Exception e) {
            throw new UserException("not found user");
        }
        return res;
    }

    private String getCurrentUser() {
        UsernamePasswordAuthenticationToken user
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        log.info("getUser");
//        log.info(user.getPrincipal().toString());
        Object principal = user.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
