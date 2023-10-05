package sang.se.bookingmovie.app.movie_status;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.movie.Movie;
import sang.se.bookingmovie.app.movie.MovieEntity;
import sang.se.bookingmovie.app.movie.MovieMapper;
import sang.se.bookingmovie.app.movie.MovieResponse;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieStatusMapper implements IMapper<MovieStatusEntity, MovieStatus, MovieStatus> {

    private final ModelMapper mapper;

    private final MovieMapper movieMapper;

    private final ObjectsValidator<MovieStatus> validator;


    @Override
    public MovieStatusEntity requestToEntity(MovieStatus movieStatus) {
        validator.validate(movieStatus);
        return mapper.map(movieStatus, MovieStatusEntity.class);
    }

    @Override
    public MovieStatus entityToResponse(MovieStatusEntity movieStatusEntity) {
        return mapper.map(movieStatusEntity, MovieStatus.class);
    }
}
