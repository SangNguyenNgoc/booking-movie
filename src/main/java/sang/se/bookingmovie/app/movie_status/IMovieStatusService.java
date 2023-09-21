package sang.se.bookingmovie.app.movie_status;

import sang.se.bookingmovie.response.ListResponse;

public interface IMovieStatusService {

    String create(MovieStatus movieStatus);

    ListResponse getAll();
}
