package ptit.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptit.blog.model.user.User;

import java.util.Date;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.verificationCode = :verificationCode")
    User findByVerificationCode(@Param("verificationCode") String verificationCode);

    @Query("select u from User u where u.resetPasswordCode = :resetPasswordCode")
    User findByResetPasswordCode(@Param("resetPasswordCode") String resetPasswordCode);

    @Query("select u from User u WHERE (:contains is null or (u.username LIKE %:contains% OR u.email LIKE %:contains% ))" +
            "AND (:isActive is null or :isActive = u.isActive)" +
            "AND (:fromDate is null or :fromDate <= u.updatedAt)" +
            "AND (:toDate is null or :toDate >= u.updatedAt)")
    Page<User> search(@Param("contains") String contains,
                      @Param("isActive") Boolean isActive,
                      @Param("fromDate") Date fromDate,
                      @Param("toDate") Date toDate,
                      Pageable pageable);
}
