package ptit.blog.exception.role;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ptit.blog.response.ResponseObject;

@ControllerAdvice
public class RoleNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(RoleNotFoundUsernameException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseObject<Object> roleNotFoundUsernameHandler(RoleNotFoundUsernameException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.USER_NOT_FOUND, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(RoleReqEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseObject<Object> roleReqEmptyHandler(RoleReqEmptyException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(RoleAuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseObject<Object> roleAuthorizationHandler(RoleAuthorizationException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.UNAUTHORIZE, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(RoleDataAccessException.class)
    @ResponseStatus(HttpStatus.OK)
    ResponseObject<Object> RoleDataAccessHandler(RoleDataAccessException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.UNHANDLED_ERROR, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ErrorEditRoleException.class)
    @ResponseStatus(HttpStatus.OK)
    ResponseObject<Object> ErrorEditRoleHandler(ErrorEditRoleException ex) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.UNHANDLED_ERROR, ex.getMessage());
    }

}
