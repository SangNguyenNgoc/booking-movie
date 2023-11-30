package sang.se.bookingmovie.app.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @NotBlank(message = "Name MUST not be blank")
    private String fullName;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password MUST not be blank")
    private String password;


}
