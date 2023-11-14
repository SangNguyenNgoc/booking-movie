package sang.se.bookingmovie.app.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyRequest {

    @NotBlank(message = "Verify code MUST not be blank")
    private String verify;
}
