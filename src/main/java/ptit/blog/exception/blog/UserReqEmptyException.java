package ptit.blog.exception.blog;

public class UserReqEmptyException extends RuntimeException {
    public UserReqEmptyException() {
        super("staff request empty");
    }
}
