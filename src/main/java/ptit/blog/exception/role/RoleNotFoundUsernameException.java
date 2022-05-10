package ptit.blog.exception.role;

public class RoleNotFoundUsernameException extends RuntimeException {
    public RoleNotFoundUsernameException(String username) {
        super("Not found username: " + username);
    }
}
