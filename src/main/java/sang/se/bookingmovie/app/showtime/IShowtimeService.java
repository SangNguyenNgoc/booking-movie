package sang.se.bookingmovie.app.showtime;

import sang.se.bookingmovie.response.ListResponse;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface IShowtimeService {
    String create(List<ShowtimeRequest> showtimeRequest);

    ListResponse getShowtimeByCinemaAndDate(Date date, String cinemaId);

    ListResponse getShowtimeByMovie(Date date, String movieId);

    void updateStatusOfShowtime (LocalDate currentDate, LocalTime currentTime);
}