package sang.se.bookingmovie.app.bill;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Digits(integer = 2, fraction = 0, message = "Changed point MUST be a number")
    @Max(value = 50, message = "Changed point MUST be less than or equal to 18")
    private Integer changedPoint;

    @NotBlank(message = "Showtime id MUST not be blank")
    private String showtimeId;

    @NotEmpty(message = "seat MUST not be empty")
    @NotNull(message = "seat MUST not be null")
    private List<Integer> seatId;

}
