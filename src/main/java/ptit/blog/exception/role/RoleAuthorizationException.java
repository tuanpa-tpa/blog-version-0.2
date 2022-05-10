package ptit.blog.exception.role;

public class RoleAuthorizationException extends RuntimeException {
    public RoleAuthorizationException(String username) {
        super("username " + username + " unauthorized");
    }
}
