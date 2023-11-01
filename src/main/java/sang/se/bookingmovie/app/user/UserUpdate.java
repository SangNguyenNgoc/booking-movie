package sang.se.bookingmovie.app.user;

import jakarta.annotation.Nullable;
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
public class UserUpdate {

    private String fullName;

    @Past(message = "Date of birth MUST be past")
    private Date dateOfBirth;

    private String gender;

    @Pattern(regexp = "^[0-9]+$", message = "Invalid number phone")
    private String phoneNumber;

    @Email
    private String email;

}
