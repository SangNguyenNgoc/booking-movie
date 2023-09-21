package sang.se.bookingmovie.app.movie_status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.movie_genre.MovieGenreEntity;
import sang.se.bookingmovie.app.movie_genre.MovieGenreMapper;
import sang.se.bookingmovie.app.movie_genre.MovieGenreRepository;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieStatusService implements IMovieStatusService{


    private final MovieStatusRepository movieStatusRepository;

    private final MovieStatusMapper mapper;

    private final ApplicationUtil util;

    @Override
    public String create(MovieStatus movieStatus) {
        MovieStatusEntity movieStatusEntity = mapper.requestToEntity(movieStatus);
        movieStatusEntity.setSlug(util.toSlug(movieStatus.getDescription()));
        movieStatusRepository.save(movieStatusEntity);
        return "Success";
    }

    @Override
    public ListResponse getAll() {
        List<MovieStatusEntity> movieStatusEntities = movieStatusRepository.findAll();
        return ListResponse.builder()
                .total(movieStatusEntities.size())
                .data(movieStatusEntities.stream()
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
