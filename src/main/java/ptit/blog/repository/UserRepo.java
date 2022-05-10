package ptit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptit.blog.model.user.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.verificationCode = :verificationCode")
    User findByVerificationCode(@Param("verificationCode") String verificationCode);

    @Query("select u from User u where u.resetPasswordCode = :resetPasswordCode")
    User findByResetPasswordCode(@Param("resetPasswordCode") String resetPasswordCode);
}
