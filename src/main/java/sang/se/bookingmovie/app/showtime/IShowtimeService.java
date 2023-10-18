package sang.se.bookingmovie.app.showtime;

import sang.se.bookingmovie.response.ListResponse;


import java.sql.Date;
import java.util.List;

public interface IShowtimeService {
    String create(List<ShowtimeRequest> showtimeRequest);
    ListResponse getShowtimeByCinemaAndDate(Date date, String cinemaId);

    ListResponse getShowtimeByMovie(Date date, String movieId);
}
