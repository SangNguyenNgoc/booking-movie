package sang.se.bookingmovie.app.movie;

import jakarta.persistence.criteria.CriteriaBuilder;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusEntity;

import java.util.List;

public interface IMovieService {

    MovieStatus getMoviesByStatus(String slug);

    List<MovieStatus> getMoviesByStatus();
}
