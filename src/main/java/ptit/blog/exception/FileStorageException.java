package ptit.blog.exception;
/* Created by hunghust97 on 07/10/2021 - 2:39 PM
 *@project csignremote
 *@author Lab Security - CIST
 *@web http://cist.cmc.com.vn/
 */

public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}