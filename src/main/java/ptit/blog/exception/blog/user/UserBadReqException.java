package ptit.blog.exception.blog.user;

public class UserBadReqException extends RuntimeException {
    public UserBadReqException(String message) {
        super(message);
    }
}
