package sang.se.bookingmovie.auth;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sang.se.bookingmovie.app.role.RoleEntity;
import sang.se.bookingmovie.app.role.RoleRepository;
import sang.se.bookingmovie.app.user.*;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.EmailService;
import sang.se.bookingmovie.utils.JwtService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationUtil applicationUtil;

    private final TemplateEngine templateEngine;

    private final EmailService emailService;

    @Value("${oauth2.targetUrl}")
    private String targetUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User userDetails) {
            getRedirectStrategy().sendRedirect(
                    request,
                    response,
                    determineTargetUrl(createToken(userDetails))
            );
        }
    }

    public AuthResponse createToken(DefaultOAuth2User userDetails) {
        String email = userDetails.getAttribute("email");
        var user = userRepository.findByEmail(email)
                .orElse(null);
        if (user == null) {
            String pass = applicationUtil.generateRandomString(6);
            RoleEntity roleEntity = roleRepository.findById(2).orElseThrow();
            var userRegister = UserEntity.builder()
                    .fullName(userDetails.getAttribute("name"))
                    .email(email)
                    .avatar(userDetails.getAttribute("picture"))
                    .password(passwordEncoder.encode(pass))
                    .createDate(applicationUtil.getDateNow())
                    .id(applicationUtil.createUUID(email + applicationUtil.getDateNow()))
                    .gender(Gender.UNKNOWN)
                    .role(roleEntity)
                    .point(0)
                    .verify(true)
                    .build();
            userRepository.save(userRegister);
            sendPass(userRegister, pass);
            return AuthResponse.builder()
                    .token(jwtService.generateToken(userRegister))
                    .user(userMapper.entityToResponse(userRegister))
                    .exist(false)
                    .build();
        } else {
            RoleEntity roleEntity = roleRepository.findById(2).orElseThrow();
            userRepository.updateVerifyAndRoleByEmail(true, roleEntity, user.getEmail());
            return AuthResponse.builder()
                    .token(jwtService.generateToken(user))
                    .user(userMapper.entityToResponse(user))
                    .exist(true)
                    .build();
        }
    }

    protected String determineTargetUrl(AuthResponse authResponse) {
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", authResponse.getToken())
                .queryParam("exist",  authResponse.getExist() ? 1 : 0)
                .build()
                .toUriString();
    }

    public void sendPass(UserEntity userEntity, String pass) {
        Context context = new Context();
        context.setVariables(Map.of(
                "name", userEntity.getFullName(),
                "mail", userEntity.getEmail(),
                "pass", pass
        ));
        String text = templateEngine.process("EmailPassTemplate", context);
        try {
            emailService.sendEmailHtml(userEntity.getEmail(), "Mật khẩu tài khoản The Cinema của bạn", text);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
