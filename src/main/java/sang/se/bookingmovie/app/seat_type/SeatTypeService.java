package sang.se.bookingmovie.app.seat_type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatTypeService implements ISeatTypeService{
    private final SeatTypeRepository seatTypeRepository;
}
