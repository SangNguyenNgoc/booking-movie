package sang.se.bookingmovie.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VerifyPassEvent {
    private String id;
    private String email;
    private String name;
    private String verifyCode;
}
