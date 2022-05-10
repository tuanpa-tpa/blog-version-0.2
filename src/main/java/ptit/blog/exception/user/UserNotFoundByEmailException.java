package ptit.blog.exception.user;

public class UserNotFoundByEmailException extends RuntimeException {
    public UserNotFoundByEmailException(String email) {
        super("User not found by " + email);
    }
}
