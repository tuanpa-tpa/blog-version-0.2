package ptit.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ptit.blog.response.ResponseObject;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseObject handlerNotFoundException(NotFoundException e, WebRequest req) {
        return new ResponseObject<>(false, ptit.blog.response.ResponseStatus.UNHANDLED_ERROR, e.getLocalizedMessage());
    }
}
