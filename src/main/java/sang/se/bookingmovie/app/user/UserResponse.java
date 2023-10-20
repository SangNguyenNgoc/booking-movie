package sang.se.bookingmovie.app.user;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserResponse {
    @JsonProperty("full_name") private String fullName;
    private String email;
    @JsonProperty("date_of_birth") private String dateOfBirth;
    @JsonProperty("phone_number") private String phoneNumber;
    private Boolean gender;
    private Integer point;
    private Boolean verify;
    private String role;
}
