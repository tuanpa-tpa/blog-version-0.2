package ptit.blog.exception.blog.user;

public class UserIsSetSubcriberException extends RuntimeException{
    public UserIsSetSubcriberException(String username){
        super("User with username: "+username+" is already a Subscriber !");
    }
}