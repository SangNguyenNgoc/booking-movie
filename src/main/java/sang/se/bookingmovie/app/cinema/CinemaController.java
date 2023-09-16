package sang.se.bookingmovie.app.cinema;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;
}
