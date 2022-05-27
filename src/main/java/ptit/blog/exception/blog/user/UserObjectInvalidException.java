package ptit.blog.exception.blog.user;

public class UserObjectInvalidException extends RuntimeException {
    public UserObjectInvalidException(String message) {
        super(message);
    }
}
