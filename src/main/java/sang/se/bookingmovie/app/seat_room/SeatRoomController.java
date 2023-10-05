package sang.se.bookingmovie.app.seat_room;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class SeatRoomController {
    private final SeatRoomService seatRoomService;
}
