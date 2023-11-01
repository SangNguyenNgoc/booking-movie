package sang.se.bookingmovie.app.movie;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.response.ListResponse;

import java.time.LocalDate;
import java.util.List;

public interface IMovieService {

    String create(String movieJson, MultipartFile poster, MultipartFile horPoster, List<MultipartFile> images);

    ListResponse getMoviesByStatus(String slug, Integer page, Integer size);

    ListResponse getAll(Integer page, Integer size);

    MovieResponse getMovieById(String movieId);

    MovieResponse getMovieBySlug(String slug);

    String updateStatusOfMovie(String movieId, Integer statusId);

    void updateStatusOfMovie(LocalDate currentDate);

    String updateMovie(String movieId, Movie movie);

    String updatePoster(String movieId, MultipartFile poster);

    String updateHorPoster(String movieId, MultipartFile poster);

    String updateImages(String movieId, List<MultipartFile> images, List<Integer> imageIds);

    ListResponse searchBySlug(String input, Integer page, Integer size);

    String updateMovie(String movieId, String movieJson, List<MultipartFile> images, List<Integer> imageIds, MultipartFile poster, MultipartFile horPoster);
}
