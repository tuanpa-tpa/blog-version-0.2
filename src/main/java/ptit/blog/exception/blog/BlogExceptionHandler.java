package ptit.blog.exception.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ptit.blog.response.ResponseObject;

@ControllerAdvice
public class BlogExceptionHandler {
    @ResponseBody
    @ExceptionHandler(UserNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseObject<Object> userNotFoundByIdHandler(UserNotFoundByIdException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.USER_NOT_FOUND, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundByEmailException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseObject<Object> userNotFoundByEmailHandler(UserNotFoundByEmailException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.USER_DISABLE, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserReqEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseObject<Object> userReqEmptyHandler(UserReqEmptyException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserAuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseObject<Object> userAuthorizationHandler(UserAuthorizationException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.UNAUTHORIZE, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserDataAccessException.class)
    @ResponseStatus(HttpStatus.OK)
    ResponseObject<Object> userDataAccessHandler(UserDataAccessException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.UNHANDLED_ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BlogException.class)
    @ResponseStatus(HttpStatus.OK)
    ResponseObject<Object> userHandler(BlogException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.UNHANDLED_ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserBadReqException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseObject<Object> userBadReqHandler(UserBadReqException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserObjectInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseObject<Object> userObjectInvalidExceptionHandler(UserObjectInvalidException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.OBJECT_INVALID, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseObject<Object> userNotFoundUsernameHandler(UserNotFoundUsernameException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.USER_NOT_FOUND, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserIsSetSubcriberException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseObject<Object> userIsSetSubcriberHandler(UserIsSetSubcriberException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.UNAUTHORIZE, ex.getMessage());
    }

}
