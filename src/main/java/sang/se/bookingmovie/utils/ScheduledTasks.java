package sang.se.bookingmovie.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.movie.MovieService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ScheduledTasks {

    private final MovieService movieService;

    @PostConstruct
    @Scheduled(cron = "0 0 2 * * ?")
    public void setMovieStatus() {
        LocalDate currentDate = LocalDate.now();
        movieService.updateStatusOfMovie(currentDate);
    }
}
