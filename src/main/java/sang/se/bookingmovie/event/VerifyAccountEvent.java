package sang.se.bookingmovie.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VerifyAccountEvent {
    private String email;
    private String verifyCode;
}
