package sang.se.bookingmovie.app.cinema;

import sang.se.bookingmovie.response.ListResponse;

public interface ICinemaService {
    String create(Cinema cinemaRequest);

    ListResponse getAll(Integer page, Integer size);

    Cinema getById(String cinemaId);

    CinemaResponse getByIdInAdmin(String cinemaId);

    String update(Cinema cinemaRequest, String cinemaId);

    String createCinemaWithRoom(CinemaRequest cinemaRequest);
}
