package ptit.blog.exception.user;

public class UserNotFoundUsernameException extends RuntimeException {
    public UserNotFoundUsernameException(String username) {
        super("Not found username: " + username);
    }
}
