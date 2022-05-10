package ptit.blog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailRegistrationDto {
    private String from;
    private String to;
    private String subject;
    @Data
    @Getter
    @Setter
    @Builder
    public static class ContentEmail{
        private String name;
        private String email;
        private String password;
        private String role;
        public String verificationCode;
    }
    @Data
    @Getter
    @Setter
    @Builder
    public static class ContentEmailResetPassword {
        private String name;
        private String email;
        private String password;
        private String role;
        public String resetPasswordCode;
    }
}
