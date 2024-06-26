package sang.se.bookingmovie.app.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.cinema.CinemaResponse;
import sang.se.bookingmovie.app.room_status.RoomStatus;
import sang.se.bookingmovie.app.seat_room.SeatRoomResponse;
import sang.se.bookingmovie.app.showtime.ShowtimeResponse;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomResponse {
    private String id;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RoomStatus status;

    private Integer totalSeats;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SeatRoomResponse> seats;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CinemaResponse cinema;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ShowtimeResponse> showtimes;

}
