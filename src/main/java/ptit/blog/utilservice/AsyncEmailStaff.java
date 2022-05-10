//package ptit.blog.utilservice;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import ptit.blog.dto.EmailRegistrationDto;
//import ptit.blog.repository.RoleRepo;
//import ptit.blog.repository.UserRepo;
//import ptit.blog.service.UserService;
//
//import java.security.SecureRandom;
//import java.util.*;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class AsyncEmailStaff {
//    private final UserRepo userRepo;
//    private final PasswordEncoder passwordEncoder;
//    private final RoleRepo roleRepo;
//    private final SendEmailService sendEmailService;
//    public static List<EmailRegistrationDto.ContentEmail> userErrors;
////    private final PushNotificationService pushNotificationService;
//    private final UserService userService;
////    private final TokenDeviceService tokenDeviceService;
//    private final ModelMapper modelMapper;
////    private final MessageFireBaseService messageFireBaseService;
//    static SecureRandom rnd = new SecureRandom();
//    @Value("${spring.mail.username}")
//    private String emailFrom;
//
////    @Async("executor")
////    public void asyncExtractedStaff(CreateListStaffReq req, Organization organization, SubscriberCategory subscriberCategory, UserDto userDto) {
////        String password = RandomPassword.randomText(15);
////        log.info("Mat khau tao username {}, {}", username.toString(), password);
////        //Create birthPlace Address
////
////        //Role
////        Set<Role> roles = new HashSet<>();
////        roles.add(this.roleRepo.findByRoleName("USER"));
////        //Verification Code
////        String verificationCode = RandomString.make(64);
//////            staff = this.staffRepo.saveAndFlush(staff);
////        //Create new User
////        User user = User.builder()
////                .username(username.toString())
////                .password(passwordEncoder.encode(password))
////                .email(staffInfo.getEmail())
////                .subscriberType(SubscriberType.STAFF)
////                .subscriberId(staff.getStaffId())
////                .isActive(false)
////                .verificationCode(verificationCode)
////                .roles(roles)
////                .createdAt(new Date())
////                .updatedAt(new Date())
////                .build();
////        user = this.userRepo.saveAndFlush(user);
//////            staff.setUser(user);
//////            this.staffRepo.saveAndFlush(staff);
////////                newUsers.add(user);
////
////        EmailRegistrationDto emailRequest = EmailRegistrationDto.builder()
////                .from(emailFrom)
////                .to(user.getEmail())
////                .subject("Vui lòng xác thực tài khoản !").build();
////        EmailRegistrationDto.ContentEmail contentEmail = EmailRegistrationDto.ContentEmail.builder()
////                .name(user.getUsername())
////                .email(user.getEmail())
////                .password(password)
////                .role(StringUtils.join(roles.stream().map(Role::getRoleName).collect(Collectors.toList()), ", "))
////                .verificationCode(verificationCode)
////                .build();
////        long start = System.currentTimeMillis();
////        sendEmailService.sendEmailRegistration(emailRequest, contentEmail, user.getUserId());
////        log.info("Staff:: elapsed time: " + (System.currentTimeMillis() - start));
//////
//////        List<CreateListStaffResp.StaffInfoResp> invalidStaffInfo = new ArrayList<>();
//////        //check if email is duplicate in request
//////        Map<String, Integer> emailFrequency = new LinkedHashMap<>();
//////        for (CreateListStaffReq.StaffInfoReq staffInfoReq: req.getStaffList()){
//////            String email = staffInfoReq.getEmail();
//////            if (!emailFrequency.containsKey(email))
//////                emailFrequency.put(email, 1);
//////            else
//////                emailFrequency.put(email, emailFrequency.get(email)+1);
//////        }
//////        for (String email : emailFrequency.keySet()){
//////            if (emailFrequency.get(email) != 1){
//////                for (CreateListStaffReq.StaffInfoReq staffInfoReq: req.getStaffList()){
//////                    if (staffInfoReq.getEmail().equals(email)) {
//////                        invalidStaffInfo.add(MapperIp.convertListStaffReqTpListStaffResp(staffInfoReq, organization, subscriberCategory));
//////                    }
//////                }
//////            }
//////        }
////        //return list of invalid staff info to client if there are invalid saff infos
//////        if (invalidStaffInfo.size() != 0){
//////            log.info("error 1");
//////            TokenDevice tokenDevice = tokenDeviceService.getTokenDevice(modelMapper.map(userDto, User.class));
//////            PushNotificationRequest request = new PushNotificationRequest();
////////            tokenDevice.getUser().getMessages().forEach(message -> {
////////                if (message.getToken().equals(tokenDevice.getWebToken())) {
////////                    request.setToken(message.getToken()+ invalidStaffInfo.size());
////////                    request.setBody(message.getBody());
////////                    pushNotificationService.sendPushNotificationToToken(request);
////////                    for (CreateListStaffResp.StaffInfoResp u : invalidStaffInfo) {
////////                        request.setBody("Error: " + u.getEmail());
////////                        pushNotificationService.sendPushNotificationToToken(request);
////////                    }
////////                }  else if (message.getToken().equals(tokenDevice.getDesktopToken())) {
////////                    log.info("desktop chua phat trien");
////////                } else if (message.getToken().equals(tokenDevice.getIosToken())) {
////////                    log.info("ios chua phat trien");
////////                } else {
////////                    log.info("android chua phat trien");
////////                }
////////            });
//////            return;
//////        }
////        //check if email is duplicate in database
//////        for (CreateListStaffReq.StaffInfoReq staffInfoReq: req.getStaffList()){
//////            User user = userRepo.findByEmail(staffInfoReq.getEmail());
//////            if (user != null)
//////                invalidStaffInfo.add(MapperIp.convertListStaffReqTpListStaffResp(staffInfoReq, organization, subscriberCategory));
//////        }
////        //return list of invalid staff info to client if there are invalid saff infos
//////        if (invalidStaffInfo.size() != 0){
//////            log.info("error 2");
//////            TokenDevice tokenDevice = tokenDeviceService.getTokenDevice(modelMapper.map(userDto, User.class));
//////            PushNotificationRequest request = new PushNotificationRequest();
////////            tokenDevice.getMessages().forEach(messageFirebase -> {
////////                log.info(messageFirebase.getToken());
////////                log.info(tokenDevice.getWebToken());
////////                log.info("-----");
////////            });
////////            tokenDevice.getMessages().forEach(message -> {
////////                if (message.getToken().equals(tokenDevice.getWebToken())) {
////////                    request.setToken(message.getToken());
////////                    request.setBody(message.getBody() + invalidStaffInfo.size());
////////                    pushNotificationService.sendPushNotificationToToken(request);
////////                    for (CreateListStaffResp.StaffInfoResp u : invalidStaffInfo) {
////////                        request.setBody("Error: " + u.getEmail());
////////                        pushNotificationService.sendPushNotificationToToken(request);
////////                    }
////////                } else if (message.getToken().equals(tokenDevice.getDesktopToken())) {
////////                    log.info("desktop chua phat trien");
////////                } else if (message.getToken().equals(tokenDevice.getIosToken())) {
////////                    log.info("ios chua phat trien");
////////                } else {
////////                    log.info("android chua phat trien");
////////                }
////////            });
//////            return;
//////        }
////
////
//////        userErrors = new ArrayList<>();
////        log.info("Execute method with configured executor - "
////                + Thread.currentThread().getName());
//////        for (CreateListStaffReq.StaffInfoReq staffInfo : req.getStaffList()) {
//////            //Create username
//////            StringBuilder username = new StringBuilder(organization.getOrganizationName() + "1000");
//////            //if username is not unique, then recreate another username
//////            while (userRepo.findByUsername(username.toString()) != null) {
//////                username = new StringBuilder(organization.getOrganizationName());
//////                for (int i = 0; i < 4; i++) {
//////                    username.append(rnd.nextInt(10));
//////                }
//////            }
//////            //Create password
//////
//////            if (!userErrors.isEmpty()) {
////////                TokenDevice tokenDevice = tokenDeviceService.getTokenDevice(modelMapper.map(userDto, User.class));
////////                PushNotificationRequest request = new PushNotificationRequest();
//////////                tokenDevice.getMessages().forEach(message -> {
//////////                    if (message.getToken().equals(tokenDevice.getWebToken())) {
//////////                        request.setToken(message.getToken());
//////////                        request.setBody(message.getBody() + userErrors.size());
//////////                        pushNotificationService.sendPushNotificationToToken(request);
//////////                        for (EmailRegistrationDto.ContentEmail u : userErrors) {
//////////                            request.setBody("Error: " + u.getEmail());
//////////                            pushNotificationService.sendPushNotificationToToken(request);
//////////                        }
//////////                    } else if (message.getToken().equals(tokenDevice.getDesktopToken())) {
//////////                        log.info("desktop chua phat trien");
//////////                    } else if (message.getToken().equals(tokenDevice.getIosToken())) {
//////////                        log.info("ios chua phat trien");
//////////                    } else {
//////////                        log.info("android chua phat trien");
//////////                    }
//////////                });
//////            } else {
//////                log.info("Success");
//////            }
//////
//////        }
////    }
//
//}
