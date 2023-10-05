package sang.se.bookingmovie.app.cinema;

import sang.se.bookingmovie.response.ListResponse;

public interface ICinemaService {
    String create(Cinema cinemaRequest);

    ListResponse getAll();
}
