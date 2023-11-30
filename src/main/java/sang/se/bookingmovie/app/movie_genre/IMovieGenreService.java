package sang.se.bookingmovie.app.movie_genre;

import sang.se.bookingmovie.response.ListResponse;

public interface IMovieGenreService {

    String create(MovieGenre movieGenreRequest);

    ListResponse getAll();
}
