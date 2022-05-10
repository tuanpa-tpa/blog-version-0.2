package ptit.blog.exception.role;

import org.springframework.dao.DataAccessException;

public class RoleDataAccessException extends DataAccessException {
    public RoleDataAccessException(String msg) {
        super(msg);
    }
}
