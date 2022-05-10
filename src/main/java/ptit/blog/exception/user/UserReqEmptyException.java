package ptit.blog.exception.user;

public class UserReqEmptyException extends RuntimeException {
    public UserReqEmptyException() {
        super("staff request empty");
    }
}
