package ptit.blog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import ptit.blog.config.authentication.jwtConfig.JwtTokenUtil;
import ptit.blog.dto.jwt.JwtRequest;
import ptit.blog.dto.jwt.JwtResponse;
import ptit.blog.exception.jwt.JwtBadCredentialException;
import ptit.blog.exception.jwt.JwtDisabledException;
import ptit.blog.exception.user.UserNotFoundByEmailException;
import ptit.blog.model.CustomUserPrincipal;
import ptit.blog.model.user.User;
import ptit.blog.repository.UserRepo;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponseStatus;
import ptit.blog.service.JwtUserDetailsService;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@CrossOrigin
@Slf4j
@Api(tags = "IP")
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final UserRepo userRepo;

    @ApiOperation(value = "Tạo token xác thực", response = JwtResponse.class)
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws DisabledException {
        log.info("Controller: Tạo token xác thực {}", authenticationRequest.toString());
        try {
            final User user = this.userRepo.findByEmail(authenticationRequest.getEmail());
            if (user == null) {
                log.error("user not found");
                throw new UserNotFoundByEmailException(authenticationRequest.getEmail());
            }
            this.authenticate(user.getUsername(), authenticationRequest.getPassword());
            final CustomUserPrincipal userDetails = jwtUserDetailsService.loadUserByUsername(user.getUsername().toString());
            final String access_token = jwtTokenUtil.generateToken(userDetails);
            ArrayList<GrantedAuthority> role = new ArrayList<>(userDetails.getAuthorities());
            role.removeIf(s -> !s.getAuthority().contains("ROLE_"));
            ResponseObject<UserJwt> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
            UserJwt userJwt = UserJwt.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .token(access_token)
                    .name(user.getName())
                    .role(this.roleToArrayList(role))
                    .build();
            res.setData(userJwt);
            return ResponseEntity.ok(res);
        } catch (DisabledException e) {
            throw new JwtDisabledException();
        } catch (BadCredentialsException e) {
            throw new JwtBadCredentialException();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseObject<>(false, ResponseStatus.UNAUTHORIZE, e.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseObject<?> subscriberNull(User user, CustomUserPrincipal userDetails, String access_token, ArrayList<GrantedAuthority> role) {
        ResponseObject<NullJwt> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        NullJwt nullJwt = NullJwt.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .token(access_token)
                .name(user.getName())
                .avatar("avatar-s-11.jpg")
                .role(this.roleToArrayList(role))
                .build();
        res.setData(nullJwt);
        return res;
    }
    private void authenticate(String username, String password) {
        log.info("Kiểm tra xác thực {}", username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
private ArrayList<String> roleToArrayList(ArrayList<GrantedAuthority> role){
    ArrayList<String> listRole = new ArrayList<>();
    role.forEach(grantedAuthority -> {
        listRole.add(grantedAuthority.getAuthority());
    });
    return listRole;
}
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static public class UserJwt {
        private String username;
        private String email;
        private String token;
        private String name;
        private ArrayList <String> role;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static public class NullJwt {
        private String username;
        private String email;
        private String token;
        private String avatar;
        private String name;
        private ArrayList <String> role;
    }
}

