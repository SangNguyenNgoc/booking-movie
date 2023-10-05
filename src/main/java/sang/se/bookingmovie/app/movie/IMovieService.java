package sang.se.bookingmovie.app.movie;

import jakarta.persistence.criteria.CriteriaBuilder;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusEntity;
import sang.se.bookingmovie.response.ListResponse;

import java.util.List;

public interface IMovieService {

    List<MovieStatus> getMoviesByStatus(String slug);

    String create(Movie movie);

    ListResponse getAll();
}
