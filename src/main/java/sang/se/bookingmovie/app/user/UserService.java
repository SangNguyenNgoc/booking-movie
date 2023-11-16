package sang.se.bookingmovie.app.user;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sang.se.bookingmovie.app.role.RoleEntity;
import sang.se.bookingmovie.app.role.RoleRepository;
import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.event.VerifyAccountEvent;
import sang.se.bookingmovie.event.VerifyPassEvent;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.exception.UserNotFoundException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.DiscordService;
import sang.se.bookingmovie.utils.EmailService;
import sang.se.bookingmovie.utils.JwtService;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    @Value("${verify.verify_expiration}")
    private Long verifyExpiration;

    @Value("${discord.base_avatar}")
    private String baseAvatar;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final EmailService emailService;

    private final DiscordService discordService;

    private final ApplicationUtil applicationUtil;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ObjectsValidator<VerifyRequest> verifyValidator;

    private final ObjectsValidator<ChangePassRequest> verifyPassValidator;

    private final ObjectsValidator<ResetPassRequest> verifyResetValidator;

    private final TemplateEngine templateEngine;

    @Override
    public AuthResponse register(User user) {
        checkEmail(user.getEmail());
        UserEntity userEntity = userMapper.requestToEntity(user);
        userEntity.setCreateDate(applicationUtil.getDateNow());
        userEntity.setId(applicationUtil.createUUID(userEntity.getEmail() + userEntity.getCreateDate()));
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setVerify(false);
        userEntity.setPoint(0);
        userEntity.setAvatar(baseAvatar);
        RoleEntity roleEntity = roleRepository.findById(3)
                .orElseThrow(() -> new AllException(
                        "Server error",
                        500,
                        List.of("Role is not exist")
                ));
        userEntity.setRole(roleEntity);
        userRepository.save(userEntity);
        var userResponse = userMapper.entityToResponse(userEntity);
        return AuthResponse.builder()
                .token(jwtService.generateToken(userEntity))
                .user(userResponse)
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        userMapper.validateAuth(authRequest);
        var user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Conflict", List.of("User is not exits")));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getId(),
                        authRequest.getPassword()
                )
        );
        var token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .user(userMapper.entityToResponse(user))
                .build();
    }

    @Override
    @Transactional
    public String sendToVerifyAccount(String token) {
        var userEntity = userRepository.findById(jwtService.extractSubject(jwtService.validateToken(token)))
                .orElseThrow(() -> new DataNotFoundException(
                        "Data not found",
                        List.of("User is not exist")
                ));
        userEntity.setVerifyAccount(applicationUtil.generateVerificationCode(6));
        applicationEventPublisher.publishEvent(
                VerifyAccountEvent.builder()
                        .name(userEntity.getFullName())
                        .email(userEntity.getEmail())
                        .verifyCode(userEntity.getVerifyAccount())
                        .build());
        return "Success";
    }

    @Override
    @Transactional
    public String verify(String token, VerifyRequest verify) {
        verifyValidator.validate(verify);
        var userEntity = userRepository.findById(jwtService.extractSubject(jwtService.validateToken(token)))
                .orElseThrow(() -> new AllException(
                        "Verification failure",
                        400,
                        List.of("User is not exist")
                ));
        if(userEntity.getVerifyAccount() == null) {
            throw new AllException("Verification failure", 404, List.of("Past the confirmation period"));
        }
        if(userEntity.getVerifyAccount().equals(verify.getVerify())) {
            userEntity.setVerify(true);
            userEntity.setVerifyAccount(null);
            var roleEntity = roleRepository.findById(2)
                    .orElseThrow(() -> new AllException(
                            "Server error",
                            500,
                            List.of("Role is not exist")
                    ));
            userEntity.setRole(roleEntity);
            return "Success";
        } else {
            throw new AllException("Verification failure", 401, List.of("Invalid verification code"));
        }

    }

    private void deleteVerifyAccountByEmail(String email) {
        var userEntity = userRepository.findByEmail(email).orElse(null);
        if(userEntity != null && userEntity.getVerifyAccount() != null) {
            userEntity.setVerifyAccount(null);
        }
    }

    @Override
    public UserResponse getCurrentUser(String token, String userId) {
        UserEntity userEntity;
        if(token != null) {
            userEntity = userRepository.findById(jwtService.extractSubject(jwtService.validateToken(token)))
                    .orElseThrow(() -> new AllException(
                            "Data not found",
                            404,
                            List.of("User is not exist")
                    ));
        } else {
            userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new AllException(
                            "Data not found",
                            404,
                            List.of("User is not exist")
                    ));
        }
        return userMapper.entityToResponse(userEntity);
    }

    @Override
    public ListResponse getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("createDate").descending());
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        return ListResponse.builder()
                .total(userEntities.getTotalPages())
                .data(userEntities.stream()
                        .map(userMapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public String sendToUpdateEmail(String token, String newEmail) {
        checkEmail(newEmail);
        var userEntity = getUserById(jwtService.extractSubject(jwtService.validateToken(token)));
        RoleEntity roleEntity = roleRepository.findById(3)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Role is not exist")));
        userEntity.setEmail(newEmail);
        userEntity.setRole(roleEntity);
        userEntity.setVerify(false);
        sendToVerifyAccount(token);
        return "Success";

    }


    @Override
    public Boolean checkPassword(String token, String password) {
        var userEntity = getUserById(jwtService.extractSubject(jwtService.validateToken(token)));
        return passwordEncoder.matches(password, userEntity.getPassword());
    }

    @Override
    @Transactional
    public String changePassword(String token, ChangePassRequest verifyPass) {
        verifyPassValidator.validate(verifyPass);
        var userEntity = getUserById(jwtService.extractSubject(jwtService.validateToken(token)));
        if(passwordEncoder.matches(verifyPass.getOldPass(), userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(verifyPass.getNewPass()));
            return "Success";
        } else {
            throw new AllException("Change failure", 400, List.of("Invalid password"));
        }

    }

    @Override
    @Transactional
    public String sendToResetPassword(String email) {
        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new AllException("Conflict", 409, List.of("User is not exits")));
        userEntity.setVerifyPass(applicationUtil.generateVerificationCode(6));
        applicationEventPublisher.publishEvent(
                VerifyPassEvent.builder()
                        .id(userEntity.getId())
                        .name(userEntity.getFullName())
                        .email(userEntity.getEmail())
                        .verifyCode(userEntity.getVerifyPass())
                        .build());
        return "Success";
    }

    @Override
    @Transactional
    public String resetPassword(ResetPassRequest verify) {
        verifyResetValidator.validate(verify);
        String verifySubject = jwtService.extractSubject(verify.getVerifyToken());
        String userId = verifySubject.split("_")[0];
        String verifyCode = verifySubject.split("_")[1];
        var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new AllException("Conflict", 404, List.of("User is not exits")));
        if(userEntity.getVerifyPass().equals(verifyCode)) {
            userEntity.setPassword(passwordEncoder.encode(verify.getPass()));
            userEntity.setVerifyPass(null);
            return "Success";
        } else {
            throw new AllException("Verification failure", 401, List.of("Invalid verification code"));
        }
    }

    @Override
    @Transactional
    public UserResponse updateUser(String token, UserUpdate userUpdate) {
        userMapper.validateUpdate(userUpdate);
        UserEntity userEntity = getUserById(jwtService.extractSubject(jwtService.validateToken(token)));
        if(userUpdate.getFullName() != null && !userUpdate.getFullName().isEmpty()) {
            userEntity.setFullName(userUpdate.getFullName());
        }
        if(userUpdate.getDateOfBirth() != null) {
            userEntity.setDateOfBirth(userUpdate.getDateOfBirth());
        }
        if(userUpdate.getGender() != null) {
            userEntity.setGender(userMapper.getGenderInRequest(userUpdate.getGender()));
        }
        if(userUpdate.getPhoneNumber() != null) {
            userEntity.setPhoneNumber(userUpdate.getPhoneNumber());
        }
        if(userUpdate.getEmail() != null && !userUpdate.getEmail().equals(userEntity.getEmail())) {
            sendToUpdateEmail(token, userUpdate.getEmail());
        }
        return userMapper.entityToResponse(userEntity);
    }

    @Override
    @Transactional
    public UserResponse updateAvatar(String token, MultipartFile avatar) {
        UserEntity userEntity = getUserById(jwtService.extractSubject(jwtService.validateToken(token)));
        if(avatar != null) {
            userEntity.setAvatar(discordService.sendAvatar(avatar));
        } else {
            userEntity.setAvatar(baseAvatar);
        }
        return userMapper.entityToResponse(userEntity);
    }

    @Transactional
    private void deleteVerifyPassByEmail(String email) {
        var userEntity = userRepository.findByEmail(email).orElse(null);
        if(userEntity != null && userEntity.getVerifyPass() != null) {
            userEntity.setVerifyPass(null);
        }
    }

    private void checkEmail(String email) {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new AllException("Conflict", 409, List.of("Email taken!"));
        }
    }

    private UserEntity getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Conflict", List.of("User is not exits")));
    }

    @EventListener
    @Async
    @Transactional
    public void sendToVerifyAccount(VerifyAccountEvent event)  {
        try {
            Context context = new Context();
            context.setVariables(Map.of(
                    "name", event.getName(),
                    "mail", event.getEmail(),
                    "otp", event.getVerifyCode()
            ));
            String text = templateEngine.process("EmailOTPTemplate", context);
            emailService.sendEmailHtml(event.getEmail(), "Mã xác nhận từ Cinema", text);
            Thread.sleep(verifyExpiration);
        } catch (InterruptedException | MessagingException | UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        deleteVerifyAccountByEmail(event.getEmail());
    }

    @EventListener
    @Async
    @Transactional
    public void sendToVerifyPass(VerifyPassEvent event)  {
        try {
            String param = jwtService.generateVerify(event.getId(), event.getVerifyCode());
            Context context = new Context();
            context.setVariables(Map.of(
                    "name", event.getName(),
                    "url", "https://www.pwer-dev.id.vn/forgot-password?verify=" + param
            ));
            String text = templateEngine.process("EmailURLTemplate", context);
            emailService.sendEmailHtml(event.getEmail(), "Xác nhận từ Cinema", text);
            Thread.sleep(verifyExpiration);
        } catch (InterruptedException | MessagingException | UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        deleteVerifyPassByEmail(event.getEmail());
    }

}
