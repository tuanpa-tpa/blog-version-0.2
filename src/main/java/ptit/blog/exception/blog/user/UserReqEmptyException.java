package ptit.blog.exception.blog.user;

public class UserReqEmptyException extends RuntimeException {
    public UserReqEmptyException() {
        super("staff request empty");
    }
}
