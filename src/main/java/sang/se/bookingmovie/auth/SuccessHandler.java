package sang.se.bookingmovie.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import sang.se.bookingmovie.app.role.RoleEntity;
import sang.se.bookingmovie.app.role.RoleRepository;
import sang.se.bookingmovie.app.user.*;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationUtil applicationUtil;

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
            RoleEntity roleEntity = roleRepository.findById(3).orElseThrow();
            var userRegister = UserEntity.builder()
                    .fullName(userDetails.getAttribute("name"))
                    .email(email)
                    .avatar(userDetails.getAttribute("picture"))
                    .password(passwordEncoder.encode("2a075e92-89c3-11ee-b9d1-0242ac120002"))
                    .createDate(applicationUtil.getDateNow())
                    .id(applicationUtil.createUUID(email + applicationUtil.getDateNow()))
                    .gender(Gender.UNKNOWN)
                    .role(roleEntity)
                    .point(0)
                    .verify(false)
                    .build();
            userRepository.save(userRegister);
            return AuthResponse.builder()
                    .token(jwtService.generateToken(userRegister))
                    .user(userMapper.entityToResponse(userRegister))
                    .exist(false)
                    .build();
        } else {
            return AuthResponse.builder()
                    .token(jwtService.generateToken(user))
                    .user(userMapper.entityToResponse(user))
                    .exist(true)
                    .build();
        }
    }

    protected String determineTargetUrl(AuthResponse authResponse) {

        String targetUrl = "https://www.pwer-dev.id.vn/";

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", authResponse.getToken())
                .queryParam("exist",  authResponse.getExist() ? 1 : 0)
                .build().toUriString();
    }
}
