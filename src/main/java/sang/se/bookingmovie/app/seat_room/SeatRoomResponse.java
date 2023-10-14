package sang.se.bookingmovie.app.seat_room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.seat_type.SeatType;
import sang.se.bookingmovie.app.seat_type.SeatTypeEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeatRoomResponse {
    private String id;
    private Boolean status;
    @JsonProperty("seat_id")
    private Integer seatId;
    @JsonInclude(JsonInclude.Include.NON_NULL) private SeatType type;
}
