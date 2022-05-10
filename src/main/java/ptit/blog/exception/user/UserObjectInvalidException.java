package ptit.blog.exception.user;

public class UserObjectInvalidException extends RuntimeException {
    public UserObjectInvalidException(String message) {
        super(message);
    }
}
