package ptit.blog.exception.user;

import org.springframework.dao.DataAccessException;

public class UserDataAccessException extends DataAccessException {
    public UserDataAccessException(String msg) {
        super(msg);
    }
}
