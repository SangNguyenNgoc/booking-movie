package sang.se.bookingmovie.app.movie_genre;

import sang.se.bookingmovie.response.ListResponse;

import java.util.List;

public interface IMovieGenreService {

    String create(MovieGenre movieGenreRequest);

    ListResponse getAll();
}
