package ptit.blog.exception.user;

import java.util.UUID;

public class UserNotFoundByIdException extends RuntimeException {
    public UserNotFoundByIdException(UUID id) {
        super("User not found by " + id);
    }
}
