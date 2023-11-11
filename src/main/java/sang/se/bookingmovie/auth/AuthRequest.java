package sang.se.bookingmovie.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;
}
