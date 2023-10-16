package sang.se.bookingmovie.app.user;

import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.event.VerifyMailEvent;

public interface IUserService {

    AuthResponse register(User user);

    AuthResponse verify(String email, String verifyCode);

    String sendEmailToVerify(String userId);

    void sendEmailToVerify(VerifyMailEvent event);

    void deleteVerifyCodeByEmail(String email);

    AuthResponse authenticate(AuthRequest authRequest);
}
