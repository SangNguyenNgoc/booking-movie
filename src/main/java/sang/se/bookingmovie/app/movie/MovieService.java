package sang.se.bookingmovie.app.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusEntity;
import sang.se.bookingmovie.app.movie_status.MovieStatusMapper;
import sang.se.bookingmovie.app.movie_status.MovieStatusRepository;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.response.ListResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;

    private final MovieStatusRepository movieStatusRepository;

    private final MovieMapper movieMapper;

    private final MovieStatusMapper mapper;

    @Override
    public List<MovieStatus> getMoviesByStatus(String slug) {
        List<MovieStatusEntity> movieStatusEntities = new ArrayList<>();
        if(slug != null) {
            MovieStatusEntity movieStatusEntity = movieStatusRepository.findBySlug(slug)
                    .orElseThrow(() -> new DataNotFoundException("Data not found", "status_id is not exist"));
            movieStatusEntities.add(movieStatusEntity);
        } else {
            movieStatusEntities = movieStatusRepository.findAll();
        }
        return movieStatusEntities.stream()
                .peek(movieStatus -> movieStatus.getMovies().forEach(this::getFieldToLanding))
                .map(mapper::entityToResponse)
                .peek(movieStatus -> movieStatus.setMovies(sortByReleaseDate(movieStatus.getMovies())))
                .collect(Collectors.toList());
    }

    @Override
    public String create(Movie movie) {
        return null;
    }

    @Override
    public ListResponse getAll() {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        return ListResponse.builder()
                .total(movieEntities.size())
                .data(movieEntities.stream()
                        .peek(this::getFieldToAdmin)
                        .map(movieMapper::entityToResponse)
                        .sorted(Comparator.comparing(MovieResponse::getReleaseDate))
                        .collect(Collectors.toList()))
                .build();
    }

    private List<MovieResponse> sortByReleaseDate(List<MovieResponse> movies) {
        return movies.stream()
                .sorted(Comparator.comparing(MovieResponse::getReleaseDate))
                .toList();
    }

    private void getFieldToLanding(MovieEntity movieEntity) {
        movieEntity.setStatus(null);
        movieEntity.setShowtimes(null);
        movieEntity.setComments(null);
        movieEntity.setImages(null);
        movieEntity.setFormats(null);
        movieEntity.setGenre(null);
    }

    private void getFieldToAdmin(MovieEntity movieEntity) {
        movieEntity.setShowtimes(null);
        movieEntity.getStatus().setMovies(null);
        movieEntity.getGenre().setMovies(null);
    }

}

