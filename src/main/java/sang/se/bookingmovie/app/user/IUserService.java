package sang.se.bookingmovie.app.user;

import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.response.ListResponse;

public interface IUserService {

    AuthResponse register(User user);

    AuthResponse authenticate(AuthRequest authRequest);

    String sendToVerifyAccount(String userId);

    String verify(String email, String verifyCode);

    void deleteVerifyAccountByEmail(String email);

    void deleteVerifyMailByEmail(String email);

    UserResponse getCurrentUser(String token, String email);

    ListResponse getAll();

    UserResponse updateEmail(String token, String verifyMail);

    String sendToUpdateEmail(String token,String newEmail);

    Boolean checkPassword(String token, String password);

    String changePassword(String token, String oldPassword, String newPassword);
}
