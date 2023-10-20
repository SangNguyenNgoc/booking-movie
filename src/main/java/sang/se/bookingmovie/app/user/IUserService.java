package sang.se.bookingmovie.app.user;

import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.response.ListResponse;

import java.sql.Date;

public interface IUserService {

    AuthResponse register(User user);

    AuthResponse authenticate(AuthRequest authRequest);

    String sendToVerifyAccount(String userId);

    String verify(String email, String verifyCode);

    UserResponse getCurrentUser(String token, String email);

    ListResponse getAll();

    String sendToUpdateEmail(String token,String newEmail);

    UserResponse updateEmail(String token, String verifyMail);

    Boolean checkPassword(String token, String password);

    String changePassword(String token, String oldPassword, String newPassword);

    String sendToResetPassword(String email);

    String resetPassword(String email, String verifyPass, String pass);

    UserResponse updateUser(String token, UserUpdate userUpdate);

}
