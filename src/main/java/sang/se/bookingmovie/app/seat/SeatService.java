package sang.se.bookingmovie.app.seat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService implements ISeatService{
    private final SeatRepository seatRepository;
}
