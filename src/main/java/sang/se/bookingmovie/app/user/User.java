package sang.se.bookingmovie.app.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
