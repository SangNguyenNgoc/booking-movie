package sang.se.bookingmovie.app.movie_genre;

import java.util.List;

public interface IMovieGenreService {

    String create(MovieGenre movieGenreRequest);

    List<MovieGenre> getAll();
}
