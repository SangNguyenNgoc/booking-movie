package sang.se.bookingmovie.app.seat_type;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeatType {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    @NotBlank(message = "seat type name must not be blank")
    @NotNull(message = "seat type name must not be null")
    private String name;
    @NotNull(message = "seat type price must not be null")
    private Double price;
}
