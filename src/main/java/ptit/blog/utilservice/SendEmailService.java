//package ptit.blog.utilservice;
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import ptit.blog.dto.EmailRegistrationDto;
//import ptit.blog.exception.user.UserBadReqException;
//import ptit.blog.repository.UserRepo;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class SendEmailService {
//    private final JavaMailSender mailSender;
//    private final Configuration configuration;
//    private final UserRepo userRepo;
//    @Value("${server.ip}")
//    private String ipServer;
//    @Value("${server.artifcate}")
//    private String artifcate;
//
//
//    @Async("taskExecutor")
//    public void commonInit(String templateName, EmailRegistrationDto request, EmailRegistrationDto.ContentEmail model, Long id) {
//        log.info("Execute method with configured executor - "
//                + Thread.currentThread().getName());
////        log.info("sending email");
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                    StandardCharsets.UTF_8.name());
//            ClassPathResource image = new ClassPathResource("static/file/logo.jpg");
//            ClassPathResource imageBanner = new ClassPathResource("static/file/welcome.jpg");
//            ClassPathResource css = new ClassPathResource("static/file/css.css");
//            ClassPathResource facebook = new ClassPathResource("static/file/facebook.jpg");
//            ClassPathResource twitter = new ClassPathResource("static/file/twitter.jpg");
//            Template template = configuration.getTemplate(templateName);
//            Map<String, Object> input = new HashMap<>();
//            input.put("title", "Đăng ký tài khoản thành công");
//            input.put("ipServer", ipServer);
//            input.put("artifcate", artifcate);
//            log.info(model.toString());
//            input.put("model", model);
//            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, input);
//            helper.setTo(request.getTo());
//            helper.setFrom(request.getFrom());
//            helper.setSubject(request.getSubject());
//            helper.setText(html, true);
//            helper.addInline("logocmc", image);
//            helper.addInline("welcome", imageBanner);
//            helper.addInline("facebook", facebook);
//            helper.addInline("twitter", twitter);
//            mailSender.send(message);
//        } catch (MessagingException | IOException | TemplateException e) {
//            log.error(e.toString());
//            userRepo.deleteById(id);
//            throw new UserBadReqException("Error sending email !");
//        }
//    }
//
//    @Async("taskExecutor")
//    public void sendMailResetPassword(EmailRegistrationDto request, EmailRegistrationDto.ContentEmailResetPassword model, Long id) {
//        log.info("Execute method with configured executor - "
//                + Thread.currentThread().getName());
////        log.info("sending email");
//        String templateName = "emailResetPassword.tfl";
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                    StandardCharsets.UTF_8.name());
//            ClassPathResource image = new ClassPathResource("static/file/logo.jpg");
//            ClassPathResource imageBanner = new ClassPathResource("static/file/welcome.jpg");
//            ClassPathResource css = new ClassPathResource("static/file/css.css");
//            ClassPathResource facebook = new ClassPathResource("static/file/facebook.jpg");
//            ClassPathResource twitter = new ClassPathResource("static/file/twitter.jpg");
//            Template template = configuration.getTemplate(templateName);
//            Map<String, Object> input = new HashMap<>();
//            input.put("title", "Đăng ký tài khoản thành công");
//            input.put("ipServer", ipServer);
//            input.put("artifcate", artifcate);
//            log.info(model.toString());
//            input.put("model", model);
//            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, input);
//            helper.setTo(request.getTo());
//            helper.setFrom(request.getFrom());
//            helper.setSubject(request.getSubject());
//            helper.setText(html, true);
//            helper.addInline("logocmc", image);
//            helper.addInline("welcome", imageBanner);
//            helper.addInline("facebook", facebook);
//            helper.addInline("twitter", twitter);
//            mailSender.send(message);
//        } catch (MessagingException | IOException | TemplateException e) {
//            log.error(e.toString());
//            userRepo.deleteById(id);
//            throw new UserBadReqException("Error sending email !");
//        }
//    }
//
//    @Async("taskExecutor")
//    public void sendEmailSelfRegistration(EmailRegistrationDto request, EmailRegistrationDto.ContentEmail model, Long id) {
//        commonInit("emailSelfRegistration.tfl", request, model, id);
////        log.info("Execute method with configured executor - "
////                + Thread.currentThread().getName());
////        log.info("sending email");
////        MimeMessage message = mailSender.createMimeMessage();
////        try {
////            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
////                    StandardCharsets.UTF_8.name());
////            ClassPathResource image = new ClassPathResource("static/file/logo.jpg");
////            ClassPathResource imageBanner = new ClassPathResource("static/file/welcome.jpg");
////            ClassPathResource css = new ClassPathResource("static/file/css.css");
////            ClassPathResource facebook = new ClassPathResource("static/file/facebook.jpg");
////            ClassPathResource twitter = new ClassPathResource("static/file/twitter.jpg");
////            Template template = configuration.getTemplate("emailSelfRegistration.tfl");
////            Map<String, Object> input = new HashMap<>();
////            input.put("title", "Đăng ký tài khoản thành công");
////            input.put("ipServer", ipServer);
////            input.put("artifcate", artifcate);
////            log.info(model.toString());
////            input.put("model", model);
////            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, input);
////            helper.setTo(request.getTo());
////            helper.setFrom(request.getFrom());
////            helper.setSubject(request.getSubject());
////            helper.setText(html, true);
////            helper.addInline("logocmc", image);
////            helper.addInline("welcome", imageBanner);
////            helper.addInline("facebook", facebook);
////            helper.addInline("twitter", twitter);
////            mailSender.send(message);
////        } catch (MessagingException | IOException | TemplateException e) {
////            log.error(e.toString());
////            userRepo.deleteById(id);
////            throw new UserBadReqException("Error sending email !");
////        }
//    }
//
//    @Async("taskExecutor")
//    public void sendEmailRegistration(EmailRegistrationDto request, EmailRegistrationDto.ContentEmail model, Long id) {
////            commonInit("emailRegistration.tfl", request, model, id);
//                log.info("Execute method with configured executor - "
//                + Thread.currentThread().getName());
//        log.info("sending email");
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                    StandardCharsets.UTF_8.name());
//            ClassPathResource image = new ClassPathResource("static/file/logo.jpg");
//            ClassPathResource imageBanner = new ClassPathResource("static/file/welcome.jpg");
//            ClassPathResource css = new ClassPathResource("static/file/css.css");
//            ClassPathResource facebook = new ClassPathResource("static/file/facebook.jpg");
//            ClassPathResource twitter = new ClassPathResource("static/file/twitter.jpg");
//            Template template = configuration.getTemplate("emailRegistration.tfl");
//            Map<String, Object> input = new HashMap<>();
//            input.put("title", "Đăng ký tài khoản thành công");
//            input.put("ipServer", ipServer);
//            input.put("artifcate", artifcate);
//            log.info(model.toString());
//            input.put("model", model);
//            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, input);
//            helper.setTo(request.getTo());
//            helper.setFrom(request.getFrom());
//            helper.setSubject(request.getSubject());
//            helper.setText(html, true);
//            helper.addInline("logocmc", image);
//            helper.addInline("welcome", imageBanner);
//            helper.addInline("facebook", facebook);
//            helper.addInline("twitter", twitter);
//            mailSender.send(message);
//        } catch (MessagingException | IOException | TemplateException e) {
//            log.error(e.toString());
//            AsyncEmailStaff.userErrors.add(model);
//            userRepo.deleteById(id);
//            throw new UserBadReqException("Error sending email !");
//        }
//    }
//
////    public boolean sendEmailActiveKeypair(ActiveKeypairDto request, ActiveKeypairDto.ContentEmail contentEmail) {
////        log.info("sending emailActiveKeypair");
////        MimeMessage message = mailSender.createMimeMessage();
////        try {
////            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
////                    StandardCharsets.UTF_8.name());
////            ClassPathResource image = new ClassPathResource("static/file/logo.jpg");
////            ClassPathResource facebook = new ClassPathResource("static/file/facebook.jpg");
////            ClassPathResource twitter = new ClassPathResource("static/file/twitter.jpg");
////            Template template = configuration.getTemplate("emailActiveKeypair.tfl");
////            Map<String, Object> input = new HashMap<>();
////            input.put("title", "Cập nhật chứng thư số thành công");
////            log.info(contentEmail.toString());
////            input.put("model", contentEmail);
////            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, input);
////            helper.setTo(request.getTo());
////            helper.setFrom(request.getFrom());
////            helper.setSubject(request.getSubject());
////            helper.setText(html, true);
////            helper.addInline("logocmc", image);
////            helper.addInline("facebook", facebook);
////            helper.addInline("twitter", twitter);
////            for (ActiveKeypairDto.FileAttachment file : contentEmail.getFileAttachments()) {
////                log.info(String.valueOf(file.getResource().getByteArray().length));
////                helper.addAttachment(file.getFilename(), file.getResource());
////            }
////            mailSender.send(message);
////            return true;
////        } catch (MessagingException | IOException | TemplateException e) {
////            log.info(e.getLocalizedMessage());
////            return false;
////        }
////    }
//}
