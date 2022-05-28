package ptit.blog.exception.blog;

public class UserBadReqException extends RuntimeException {
    public UserBadReqException(String message) {
        super(message);
    }
}
