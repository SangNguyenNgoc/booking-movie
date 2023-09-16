package sang.se.bookingmovie.app.cinema;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CinemaService {
    @Autowired
    private final CinemaRepository cinemaRepository;
}
