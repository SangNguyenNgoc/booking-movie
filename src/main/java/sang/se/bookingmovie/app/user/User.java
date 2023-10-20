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

    @NotNull(message = "Date of birth MUST not be null")
    @Past(message = "Date of birth MUST be past")
    private Date dateOfBirth;

    @Pattern(regexp = "^[0-9]+$", message = "Invalid number phone")
    private String phoneNumber;

    @NotNull(message = "Gender MUST not be null")
    private Boolean gender;


}
