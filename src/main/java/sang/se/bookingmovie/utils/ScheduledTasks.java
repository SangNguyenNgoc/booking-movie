package sang.se.bookingmovie.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.comment.CommentService;
import sang.se.bookingmovie.app.movie.MovieService;
import sang.se.bookingmovie.app.showtime.ShowtimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ScheduledTasks {

    private final MovieService movieService;
    private final ShowtimeService showtimeService;
    private final CommentService commentService;

    @PostConstruct
    @Scheduled(cron = "0 0 2 * * ?")
    public void setMovieStatus() {
        LocalDate currentDate = LocalDate.now();
        movieService.updateStatusOfMovie(currentDate);
    }

    @PostConstruct
    @Scheduled(fixedRate = 300000) // Chạy mỗi 5 phút
    public void changeShowtimeStatus() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        showtimeService.updateStatusOfShowtime(currentDate, currentTime);
    }

    @PostConstruct
    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteComment() {
        LocalDateTime currentDate = LocalDateTime.now();
        commentService.deleteCommentByDate(currentDate);
    }

}
