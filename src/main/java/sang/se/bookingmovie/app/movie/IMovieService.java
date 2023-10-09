package sang.se.bookingmovie.app.movie;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.multipart.MultipartFile;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusEntity;
import sang.se.bookingmovie.response.ListResponse;

import java.util.List;

public interface IMovieService {

    List<MovieStatus> getMoviesByStatus(String slug);

    String create(String movieJson, MultipartFile poster, List<MultipartFile> images);

    ListResponse getAll();

    MovieResponse getMovieBySlug(String slug);
}
