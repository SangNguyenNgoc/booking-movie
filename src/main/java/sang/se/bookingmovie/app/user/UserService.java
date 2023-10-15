package sang.se.bookingmovie.app.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.role.RoleEntity;
import sang.se.bookingmovie.app.role.RoleRepository;
import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.event.VerifyMailEvent;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.exception.ValidException;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.EmailService;
import sang.se.bookingmovie.utils.JwtService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    @Value("${verify.url}")
    private String verifyUrl;

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
    public String register(User user) {
        checkEmail(user.getEmail());
        UserEntity userEntity = userMapper.requestToEntity(user);
        userEntity.setCreateDate(applicationUtil.getDateNow());
        userEntity.setId(applicationUtil.createUUID(userEntity.getEmail() + userEntity.getCreateDate()));
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setVerify(false);
        RoleEntity roleEntity = roleRepository.findById(2)
                .orElseThrow(() -> new DataNotFoundException(
                        "Data not found",
                        List.of("Role with id 2 is not exist")
                ));
        userEntity.setRole(roleEntity);
        userRepository.save(userEntity);
        applicationEventPublisher.publishEvent(
                VerifyMailEvent.builder()
                        .email(userEntity.getEmail())
                        .userId(userEntity.getId())
                        .build());
        return "Success";
    }

    @Override
    @Transactional
    public AuthResponse verify(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new AllException(
                        "Data not found",
                        400,
                        List.of("Past the confirmation period")
                ));
        userEntity.setVerify(true);
        return AuthResponse.builder()
                .username(userEntity.getName())
                .token(jwtService.generateToken(userEntity))
                .build();
    }

    @Override
    @EventListener
    @Async
    public void sendEmailToVerify(VerifyMailEvent event)  {
        emailService.sendEmail(event.getEmail(), "Xác nhận đăng kí từ Cinema",
                "Hãy click vào liên kết sau để tiến hành xác nhận: " +
                        verifyUrl + "?id=" + event.getUserId());
        try {
            Thread.sleep(verifyExpiration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        deleteUserNotVerify(event.getUserId());
    }

    @Override
    public void deleteUserNotVerify(String userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if(userEntity != null && !userEntity.getVerify()) {
            userRepository.deleteById(userId);
        }
    }

    public void checkEmail(String email) {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new AllException("Invalid email",400,List.of("Email taken!"));
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
                .username(user.getName())
                .token(token)
                .build();
    }

}
