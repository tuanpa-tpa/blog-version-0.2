package ptit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.blog.model.user.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
