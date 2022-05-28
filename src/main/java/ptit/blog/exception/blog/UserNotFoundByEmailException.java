package ptit.blog.exception.blog;

public class UserNotFoundByEmailException extends RuntimeException {
    public UserNotFoundByEmailException(String email) {
        super("User not found by " + email);
    }
}
