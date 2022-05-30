package ptit.blog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ptit.blog.model.CustomUserPrincipal;
import ptit.blog.model.user.Constant;
import ptit.blog.model.user.User;
import ptit.blog.repository.UserRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class JwtUserDetailsService extends User implements UserDetailsService {
    private final UserRepo userRepo;

    public JwtUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

//    @SneakyThrows
    @Override
    @Transactional(readOnly=true)
    public CustomUserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepo.findByUsername(username);
        if (user != null) {
            log.info("loadUserByUsername() ", user.getUsername());
                return new CustomUserPrincipal(user, user.getPassword(), user.getIsActive(), true, true, true, this.getAuthority(user));
        } else {
            throw new UsernameNotFoundException("username not found with username = " + user.getUsername());
        }
    }

    private List<GrantedAuthority> getAuthority(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(Constant.ROLE_PREFIX + user.getRole()));
        log.info("add authorities to UserDetail for username {}", user.getUsername());
//        List<Role> roles =  new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(Constant.ROLE_PREFIX + "SUPER_ADMIN"));

//        User có nhiều role
        user.getRoles().forEach(role -> {
            log.info("add role {}", role.getRoleName());
            authorities.add(new SimpleGrantedAuthority(Constant.ROLE_PREFIX + role.getRoleName()));
//                Role co nhieu permission
            role.getPermissions().forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority(permission.getActionCode()));
            });
        });

//        Group có nhiều role
//        if (user.getGroups() != null) {
//            user.getGroups().forEach(group -> {
//                group.getRoles().forEach(role -> {
//                    authorities.add(new SimpleGrantedAuthority(Constant.ROLE_PREFIX + role.getRoleName()));
////                Role co nhieu permission
//                    role.getPermissions().forEach(permission -> {
//                        authorities.add(new SimpleGrantedAuthority(permission.getActionCode()));
//                    });
//                });
//            });
//        }
//        Remove duplicates in arraylist
        Set<GrantedAuthority> set = new HashSet<>(authorities);
        authorities.clear();
        authorities.addAll(set);
        log.info("Have {} role for user", authorities.size());
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public Boolean getIsActive() {
        return super.getIsActive();
    }

}
