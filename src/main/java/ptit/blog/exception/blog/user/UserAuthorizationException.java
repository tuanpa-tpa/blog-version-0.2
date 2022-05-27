package ptit.blog.exception.blog.user;

public class UserAuthorizationException extends RuntimeException {
    public UserAuthorizationException(String username) {
        super("username " + username + " unauthorized");
    }
}
