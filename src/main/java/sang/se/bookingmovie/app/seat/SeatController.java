package sang.se.bookingmovie.app.seat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;
}
