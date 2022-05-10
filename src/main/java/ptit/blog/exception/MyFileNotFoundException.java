package ptit.blog.exception;
/* Created by hunghust97 on 07/10/2021 - 2:39 PM
 *@project csignremote
 *@author Lab Security - CIST
 *@web http://cist.cmc.com.vn/
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException {
    public MyFileNotFoundException(String message) {
        super(message);
    }

    public MyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}