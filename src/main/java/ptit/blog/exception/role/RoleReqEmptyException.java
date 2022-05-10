package ptit.blog.exception.role;

public class RoleReqEmptyException extends RuntimeException {
    public RoleReqEmptyException() {
        super("role request empty");
    }
}
