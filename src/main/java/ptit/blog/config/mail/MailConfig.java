package ptit.blog.config.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by pat on 5/30/2022 - 2:59 PM
 *
 * @author pat
 * @project blog-version-0.2
 */
@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("tuanpa.tpa@gmail.com");
        mailSender.setPassword("nmiumslrjhdirksp");

        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return mailSender;
    }

//    @Bean
//    public JavaMailSender javaMailSender() {
//        return new JavaMailSenderImpl();
//    }
}
