package sang.se.bookingmovie.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.movie.MovieService;
import sang.se.bookingmovie.app.showtime.ShowtimeService;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ScheduledTasks {

    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    @PostConstruct
    @Scheduled(cron = "0 0 2 * * ?")
    public void setMovieStatus() {
        LocalDate currentDate = LocalDate.now();
        movieService.updateStatusOfMovie(currentDate);
    }

    @PostConstruct
    @Scheduled(fixedRate = 60000) // Chạy mỗi 60 giây
    public void changeShowtimeStatus() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        showtimeService.updateStatusOfShowtime(currentDate, currentTime);
    }
}
