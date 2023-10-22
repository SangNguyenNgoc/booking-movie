package sang.se.bookingmovie.app.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.seat_room.SeatRoomResponse;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomResponse {
    private String name;

    private List<SeatRoomResponse> seats;
}
