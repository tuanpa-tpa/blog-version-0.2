package ptit.blog.exception.blog.user;

import java.util.UUID;

public class UserNotFoundByIdException extends RuntimeException {
    public UserNotFoundByIdException(UUID id) {
        super("User not found by " + id);
    }
}
