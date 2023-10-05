package sang.se.bookingmovie.app.room;

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
    private String id;

    @NotNull(message = "room total must not be null")
    private Integer totalSeats;
}