package ptit.blog.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ptit.blog.dto.EmailRegistrationDto;
import ptit.blog.exception.blog.UserBadReqException;
import ptit.blog.repository.UserRepo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class SendEmailService {
    private final JavaMailSender mailSender;
    private final Configuration configuration;
    private final UserRepo userRepo;
    @Value("${server.blogServer}")
    private String server;

    @Async("taskExecutor")
    public void commonInit(String templateName, EmailRegistrationDto request, EmailRegistrationDto.ContentEmail model, Long id) {
        log.info("Execute method with configured executor - "
                + Thread.currentThread().getName());
//        log.info("sending email");
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            ClassPathResource facebook = new ClassPathResource("file/facebook.jpg");
            ClassPathResource twitter = new ClassPathResource("file/twitter.jpg");
            Template template = configuration.getTemplate(templateName);
            Map<String, Object> input = new HashMap<>();
            input.put("title", "Đăng ký tài khoản thành công");
            input.put("server", server);
            log.info(model.toString());
            input.put("model", model);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, input);
            helper.setTo(request.getTo());
            helper.setFrom(request.getFrom());
            helper.setSubject(request.getSubject());
            helper.setText(html, true);
            helper.addInline("facebook", facebook);
            helper.addInline("twitter", twitter);
            mailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            log.error(e.toString());
            userRepo.deleteById(id);
            throw new UserBadReqException("Error sending email !");
        }
    }

    @Async("taskExecutor")
    public void sendMailResetPassword(EmailRegistrationDto request, EmailRegistrationDto.ContentEmailResetPassword model, Long id) {
        log.info("Execute method with configured executor - "
                + Thread.currentThread().getName());
//        log.info("sending email");
        String templateName = "emailResetPassword.tfl";
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            ClassPathResource facebook = new ClassPathResource("file/facebook.jpg");
            ClassPathResource twitter = new ClassPathResource("file/twitter.jpg");
            Template template = configuration.getTemplate(templateName);
            Map<String, Object> input = new HashMap<>();
            input.put("title", "Đăng ký tài khoản thành công");
            input.put("server", server);
            log.info(model.toString());
            input.put("model", model);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, input);
            helper.setTo(request.getTo());
            helper.setFrom(request.getFrom());
            helper.setSubject(request.getSubject());
            helper.setText(html, true);
            helper.addInline("facebook", facebook);
            helper.addInline("twitter", twitter);
            mailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            log.error(e.toString());
            userRepo.deleteById(id);
            throw new UserBadReqException("Error sending email !");
        }
    }

    @Async("taskExecutor")
    public void sendEmailSelfRegistration(EmailRegistrationDto request, EmailRegistrationDto.ContentEmail model, Long id) {
        commonInit("emailSelfRegistration.tfl", request, model, id);
    }

}
