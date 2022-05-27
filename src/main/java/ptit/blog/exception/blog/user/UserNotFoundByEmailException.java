package ptit.blog.exception.blog.user;

public class UserNotFoundByEmailException extends RuntimeException {
    public UserNotFoundByEmailException(String email) {
        super("User not found by " + email);
    }
}
