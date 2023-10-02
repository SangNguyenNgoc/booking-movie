package sang.se.bookingmovie.app.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusEntity;
import sang.se.bookingmovie.app.movie_status.MovieStatusMapper;
import sang.se.bookingmovie.app.movie_status.MovieStatusRepository;
import sang.se.bookingmovie.exception.DataNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService {

    private final MovieStatusRepository movieStatusRepository;

    private final MovieStatusMapper mapper;

    @Override
    public MovieStatus getMoviesByStatus(String slug) {
        MovieStatusEntity movieStatusEntity = movieStatusRepository.findBySlug(slug)
                .orElseThrow(() -> new DataNotFoundException("Data not found", "status_id is not exist"));
        movieStatusEntity.getMovies()
                .forEach(this::getFieldToLanding);
        MovieStatus movieStatus = mapper.entityToResponse(movieStatusEntity);
        movieStatus.setMovies(sortByReleaseDate(movieStatus.getMovies()));
        return movieStatus;
    }


    @Override
    public List<MovieStatus> getMoviesByStatus() {
        List<MovieStatusEntity> movieStatusEntities = movieStatusRepository.findAll();
        movieStatusEntities.forEach(movieStatusEntity -> {
            movieStatusEntity.getMovies()
                    .forEach(this::getFieldToLanding);
        });
        return movieStatusEntities.stream()
                .map(mapper::entityToResponse)
                .peek(movieStatus -> movieStatus.setMovies(sortByReleaseDate(movieStatus.getMovies())))
                .collect(Collectors.toList());
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

}

