package ptit.blog.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
public class RandomPassword {
    private static final String number = "0123456789";
    private static final SecureRandom rnd = new SecureRandom();
    private static final String text = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String randomNumber(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(number.charAt(rnd.nextInt(number.length())));
        return sb.toString();
    }

    public static String randomText(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(text.charAt(rnd.nextInt(text.length())));
        return sb.toString();
    }
}
