package sang.se.bookingmovie.app.seat_room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatRoomService implements ISeatRoomService {
    private final SeatRoomRepository seatRoomRepository;
}
