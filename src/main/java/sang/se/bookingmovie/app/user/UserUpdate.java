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
public class UserUpdate {
    @NotEmpty(message = "Name MUST not be blank")
    private String fullName;

    @Past(message = "Date of birth MUST be past")
    private Date dateOfBirth;

    @Pattern(regexp = "^[0-9]+$", message = "Invalid number phone")
    private String phoneNumber;

    private Boolean gender;
}
