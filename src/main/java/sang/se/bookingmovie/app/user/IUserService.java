package sang.se.bookingmovie.app.user;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.event.VerifyMailEvent;

public interface IUserService {

    String register(User user);

    AuthResponse verify(String userId);

    void sendEmailToVerify(VerifyMailEvent event);

    void deleteUserNotVerify(String userId);

    AuthResponse authenticate(AuthRequest authRequest);
}
