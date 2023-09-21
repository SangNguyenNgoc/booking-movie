package sang.se.bookingmovie.app.cinema;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CinemaService implements ICinemaService {
    @Autowired
    private final CinemaRepository cinemaRepository;
}
