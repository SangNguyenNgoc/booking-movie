package sang.se.bookingmovie.app.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomReq {
    @NotNull(message = "room total must not be null")
    private Integer totalSeats;

    @NotNull(message = "room name must not be null")
    @NotBlank(message = "room name must not be blank")
    private String name;
}