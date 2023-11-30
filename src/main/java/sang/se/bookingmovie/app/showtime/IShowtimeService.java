package sang.se.bookingmovie.app.showtime;

import sang.se.bookingmovie.app.cinema.CinemaResponse;
import sang.se.bookingmovie.response.ListResponse;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IShowtimeService {
    ShowtimeResponse create(ShowtimeRequest showtimeRequest);

    ListResponse getShowtimeByCinemaAndDate(Date date, String cinemaId);

    ListResponse getShowtimeByMovie(Date date, String movieId);

    ListResponse getShowtimeByCinemaAdmin();

    void updateStatusOfShowtime(LocalDate currentDate, LocalTime currentTime);

    ShowtimeResponse getSeatInShowTime(String showtimeId);

    List<CinemaResponse> getAllCinemaDetailShowtime();

    String delete(String id);
}
