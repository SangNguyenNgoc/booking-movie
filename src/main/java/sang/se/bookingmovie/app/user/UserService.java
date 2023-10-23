package sang.se.bookingmovie.app.user;

import jakarta.persistence.criteria.CriteriaBuilder;
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
import sang.se.bookingmovie.app.role.RoleEntity;
import sang.se.bookingmovie.app.role.RoleRepository;
import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.event.VerifyAccountEvent;
import sang.se.bookingmovie.event.VerifyMailEvent;
import sang.se.bookingmovie.event.VerifyPassEvent;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.exception.UserNotFoundException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.EmailService;
import sang.se.bookingmovie.utils.JwtService;

import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
        var userResponse = userMapper.entityToResponse(userEntity);
        RoleEntity roleEntity = roleRepository.findById(3)
                .orElseThrow(() -> new AllException(
                        "Server error",
                        500,
                        List.of("Role is not exist")
                ));
        userEntity.setRole(roleEntity);
        userRepository.save(userEntity);
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
        var userEntity = userRepository.findById(jwtService.extractSubject(validateToken(token)))
                .orElseThrow(() -> new DataNotFoundException(
                        "Data not found",
                        List.of("User is not exist")
                ));
        userEntity.setVerifyAccount(applicationUtil.generateVerificationCode(6));
        applicationEventPublisher.publishEvent(
                VerifyAccountEvent.builder()
                        .email(userEntity.getEmail())
                        .verifyCode(userEntity.getVerifyAccount())
                        .build());
        return "Success";
    }

    @Override
    @Transactional
    public String verify(String token, String verifyCode) {
        var userEntity = userRepository.findById(jwtService.extractSubject(validateToken(token)))
                .orElseThrow(() -> new AllException(
                        "Verification failure",
                        400,
                        List.of("User is not exist")
                ));
        if(userEntity.getVerifyAccount() == null) {
            throw new AllException("Verification failure", 404, List.of("Past the confirmation period"));
        }
        if(userEntity.getVerifyAccount().equals(verifyCode)) {
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
    public UserResponse getCurrentUser(String token, String email) {
        UserEntity userEntity;
        if(token != null) {
            userEntity = userRepository.findById(jwtService.extractSubject(validateToken(token)))
                    .orElseThrow(() -> new AllException(
                            "Data not found",
                            404,
                            List.of("User is not exist")
                    ));
        } else {
            userEntity = userRepository.findByEmail(email)
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
        var userEntity = getUserById(jwtService.extractSubject(validateToken(token)));
        userEntity.setVerifyMail(newEmail + "/" + applicationUtil.generateVerificationCode(6));
        applicationEventPublisher.publishEvent(
                VerifyMailEvent.builder()
                        .oldEmail(userEntity.getEmail())
                        .newMail(newEmail)
                        .verifyCode(userEntity.getVerifyMail().split("/")[1])
                        .build());
        return "Success";

    }

    @Override
    @Transactional
    public UserResponse updateEmail(String token, String verifyMail) {
        var userEntity = getUserById(jwtService.extractSubject(validateToken(token)));
        if(userEntity.getVerifyMail() == null) {
            throw new AllException("Verification failure", 404, List.of("Past the confirmation period"));
        }
        if(verifyMail.equals(userEntity.getVerifyMail().split("/")[1])) {
            userEntity.setEmail(userEntity.getVerifyMail().split("/")[0]);
            userEntity.setVerifyMail(null);
            return userMapper.entityToResponse(userEntity);
        } else {
            throw new AllException("Verification failure", 401, List.of("Invalid verification code"));
        }

    }

    @Transactional
    private void deleteVerifyMailByEmail(String email) {
        var userEntity = userRepository.findByEmail(email).orElse(null);
        if(userEntity != null && userEntity.getVerifyMail() != null) {
            userEntity.setVerifyMail(null);
        }
    }

    @Override
    public Boolean checkPassword(String token, String password) {
        validateToken(token);
        var userEntity = getUserById(jwtService.extractSubject(validateToken(token)));
        return passwordEncoder.matches(password, userEntity.getPassword());
    }

    @Override
    @Transactional
    public String changePassword(String token, String oldPassword, String newPassword) {
        validateToken(token);
        var userEntity = getUserById(jwtService.extractSubject(validateToken(token)));
        if(passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(newPassword));
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
                        .email(userEntity.getEmail())
                        .verifyCode(userEntity.getVerifyPass())
                        .build());
        return "Success";
    }

    @Override
    @Transactional
    public String resetPassword(String email, String verifyPass, String pass) {
        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new AllException("Conflict", 404, List.of("User is not exits")));
        if(userEntity.getVerifyPass().equals(verifyPass)) {
            userEntity.setPassword(passwordEncoder.encode(pass));
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
        UserEntity userEntity = getUserById(jwtService.extractSubject(validateToken(token)));
        if(userUpdate.getFullName() != null) {
            userEntity.setFullName(userUpdate.getFullName());
        }
        if(userUpdate.getDateOfBirth() != null) {
            userEntity.setDateOfBirth(userUpdate.getDateOfBirth());
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

    private String validateToken(String token) {
        if(token == null || !token.startsWith("Bearer ")) {
            throw new AllException("Forbidden", 404, List.of("Access denied"));
        } else {
            token = token.substring(7);
            return token;
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
        emailService.sendEmail(event.getEmail(), "Mã xác nhận từ Cinema",
                "Vui không cung cấp mã xác nhận cho bất kì ai, nhập mã sau để xác nhận tài khoa, " +
                        "mã có hiệu lực 5 phút: " + event.getVerifyCode());
        try {
            Thread.sleep(verifyExpiration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        deleteVerifyAccountByEmail(event.getEmail());
    }

    @EventListener
    @Async
    @Transactional
    public void sendToVerifyMail(VerifyMailEvent event)  {
        emailService.sendEmail(event.getNewMail(), "Mã xác nhận từ Cinema",
                "Vui không cung cấp mã xác nhận cho bất kì ai, nhập mã sau để thay đổi email mới, " +
                        "mã có hiệu lực 5 phút: " + event.getVerifyCode());
        try {
            Thread.sleep(verifyExpiration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        deleteVerifyMailByEmail(event.getOldEmail());
    }

    @EventListener
    @Async
    @Transactional
    public void sendToVerifyPass(VerifyPassEvent event)  {
        emailService.sendEmail(event.getEmail(), "Mã xác nhận từ Cinema",
                "Vui không cung cấp mã xác nhận cho bất kì ai, nhập mã sau để đặt lại mật khẩu mới, " +
                        "mã có hiệu lực 5 phút: " + event.getVerifyCode());
        try {
            Thread.sleep(verifyExpiration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        deleteVerifyPassByEmail(event.getEmail());
    }

}
