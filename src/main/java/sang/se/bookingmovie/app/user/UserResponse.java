package sang.se.bookingmovie.app.user;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private Integer point;
    private String avatar;
    private Boolean gender;
    @JsonProperty("phone_number") private String phoneNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL) private Boolean verify;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String role;
}
