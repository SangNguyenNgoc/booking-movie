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
import sang.se.bookingmovie.app.user.UserEntity;
import sang.se.bookingmovie.app.user.UserRepository;
import sang.se.bookingmovie.app.user.UserService;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

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

    public String createToken(DefaultOAuth2User userDetails) {
        String email = userDetails.getAttribute("email");
        AuthResponse response1 = null;
        var user = userRepository.findByEmail(email)
                .orElse(null);
        if (user == null) {
            RoleEntity roleEntity = roleRepository.findById(2).orElseThrow();
            var userRegister = UserEntity.builder()
                    .fullName(userDetails.getAttribute("name"))
                    .email(email)
                    .password(passwordEncoder.encode("booking-movie"))
                    .createDate(applicationUtil.getDateNow())
                    .id(applicationUtil.createUUID(email + applicationUtil.getDateNow()))
                    .role(roleEntity)
                    .point(0)
                    .verify(null)
                    .build();
            userRepository.save(userRegister);
            return jwtService.generateToken(userRegister);
        } else {
            return jwtService.generateToken(user);
        }
    }

    protected String determineTargetUrl(String token) {

        String targetUrl = "http://localhost:3000";

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }
}
