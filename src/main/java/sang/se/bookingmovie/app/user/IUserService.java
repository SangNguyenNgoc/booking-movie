package sang.se.bookingmovie.app.user;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.multipart.MultipartFile;
import sang.se.bookingmovie.auth.AuthRequest;
import sang.se.bookingmovie.auth.AuthResponse;
import sang.se.bookingmovie.response.ListResponse;

import java.sql.Date;

public interface IUserService {

    AuthResponse register(User user);

    AuthResponse authenticate(AuthRequest authRequest);

    String sendToVerifyAccount(String userId);

    String verify(String email, VerifyRequest verify);

    UserResponse getCurrentUser(String token, String userId);

    ListResponse getAll(Integer page, Integer size);

    String sendToUpdateEmail(String token,String newEmail);

    Boolean checkPassword(String token, String password);

    String changePassword(String token, ChangePassRequest verifyPass);

    String sendToResetPassword(String email);

    String resetPassword(ResetPassRequest verify);

    UserResponse setPassToGoogleAccount(ResetPassRequest request);

    String checkUrlToReset(String verify);

    UserResponse updateUser(String token, UserUpdate userUpdate);

    UserResponse updateAvatar(String token, MultipartFile avatar);

}
