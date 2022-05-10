package ptit.blog.exception.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ptit.blog.response.ResponseObject;

@ControllerAdvice
public class JwtNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(JwtDisabledException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseObject<Object> jwtDisabledHandler() {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.USER_DISABLE);
    }

    @ResponseBody
    @ExceptionHandler(JwtBadCredentialException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseObject<Object> jwtBadCredentialHandler() {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.BAD_CREDENTIAL);
    }

    @ResponseBody
    @ExceptionHandler(JwtHasExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseObject<Object> jwtHasExpiredHandler() {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.JWT_HAS_EXPIRED);
    }

}
