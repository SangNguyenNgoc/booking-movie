package sang.se.bookingmovie.app.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.role.RoleEntity;
import sang.se.bookingmovie.app.role.RoleRepository;
import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.event.VerifyMailEvent;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.EmailService;
import sang.se.bookingmovie.utils.JwtService;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    @Value("${verify.verify_expiration}")
    private Long verifyExpiration;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final EmailService emailService;

    private final ApplicationUtil applicationUtil;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public AuthResponse register(User user) {
        checkEmail(user.getEmail());
        UserEntity userEntity = userMapper.requestToEntity(user);
        userEntity.setCreateDate(applicationUtil.getDateNow());
        userEntity.setId(applicationUtil.createUUID(userEntity.getEmail() + userEntity.getCreateDate()));
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setVerify(false);
        userEntity.setPoint(0);
        RoleEntity roleEntity = roleRepository.findById(3)
                .orElseThrow(() -> new AllException(
                        "Server error",
                        500,
                        List.of("Role is not exist")
                ));
        userEntity.setRole(roleEntity);
        userRepository.save(userEntity);
        return AuthResponse.builder()
                .username(userEntity.getFullName())
                .token(jwtService.generateToken(userEntity))
                .build();
    }

    @Override
    @Transactional
    public AuthResponse verify(String token, String verifyCode) {
        var userEntity = userRepository.findById(jwtService.extractUsername(validateToken(token)))
                .orElseThrow(() -> new AllException(
                        "Verification failure",
                        400,
                        List.of("User is not exist")
                ));
        if(userEntity.getVerifyCode() == null) {
            throw new AllException("Verification failure", 400, List.of("Past the confirmation period"));
        }
        if(userEntity.getVerifyCode().equals(verifyCode)) {
            userEntity.setVerify(true);
            userEntity.setVerifyCode(null);
            var roleEntity = roleRepository.findById(2)
                    .orElseThrow(() -> new AllException(
                            "Server error",
                            500,
                            List.of("Role is not exist")
                    ));
            userEntity.setRole(roleEntity);
            return AuthResponse.builder()
                    .username(userEntity.getFullName())
                    .token(jwtService.generateToken(userEntity))
                    .build();
        } else {
            throw new AllException("Verification failure", 400, List.of("Invalid verification code"));
        }

    }

    @Override
    @EventListener
    @Async
    @Transactional
    public void sendEmailToVerify(VerifyMailEvent event)  {
        emailService.sendEmail(event.getEmail(), "Xác nhận đăng kí từ Cinema",
                "Vui không cung cấp mã xác nhận cho bất kì ai, nhập mã sau để kích hoạt tài khoản: " +
                        event.getVerifyCode() + ". Mã có hiệu lực 5 phút.");
        try {
            Thread.sleep(verifyExpiration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        deleteVerifyCodeByEmail(event.getEmail());
    }

    @Override
    @Transactional
    public String sendEmailToVerify(String token) {
        var userEntity = userRepository.findById(jwtService.extractUsername(validateToken(token)))
                .orElseThrow(() -> new DataNotFoundException(
                        "Data not found",
                        List.of("User is not exist")
                ));
        userEntity.setVerifyCode(generateVerificationCode(6));
        applicationEventPublisher.publishEvent(
                VerifyMailEvent.builder()
                        .email(userEntity.getEmail())
                        .verifyCode(userEntity.getVerifyCode())
                        .build());
        return "Success";
    }

    @Override
    public void deleteVerifyCodeByEmail(String email) {
        var userEntity = userRepository.findByEmail(email).orElse(null);
        if(userEntity != null && userEntity.getVerifyCode() != null) {
            userEntity.setVerifyCode(null);
        }
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        var user = userRepository.findByEmail(authRequest.getEmail())
                .orElse(null);
        if (user == null) {
            throw new DataNotFoundException("Data not found", List.of("User not exits"));
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getId(),
                        authRequest.getPassword()
                )
        );
        var token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .username(user.getFullName())
                .token(token)
                .build();
    }

    public void checkEmail(String email) {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new AllException("Invalid email",400,List.of("Email taken!"));
        }
    }

    public String generateVerificationCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }

        return code.toString();
    }

    public String validateToken(String token) {
        if(token == null || !token.startsWith("Bearer ")) {
            throw new AllException("Forbidden", 403, List.of("Access denied"));
        } else {
            token = token.substring(7);
            return token;
        }
    }

}
