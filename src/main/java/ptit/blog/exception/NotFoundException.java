package ptit.blog.exception;
/* Created by hunghust97 on 05/11/2021 - 5:12 PM
 *@project csign
 *@author Lab Security - CIST
 *@web http://cist.cmc.com.vn/
 */

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
