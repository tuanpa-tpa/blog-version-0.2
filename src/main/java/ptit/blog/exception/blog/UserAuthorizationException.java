package ptit.blog.exception.blog;

public class UserAuthorizationException extends RuntimeException {
    public UserAuthorizationException(String username) {
        super("username " + username + " unauthorized");
    }
}
