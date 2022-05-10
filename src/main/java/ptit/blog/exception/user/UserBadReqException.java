package ptit.blog.exception.user;

public class UserBadReqException extends RuntimeException {
    public UserBadReqException(String message) {
        super(message);
    }
}
