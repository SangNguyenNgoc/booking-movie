package sang.se.bookingmovie.app.movie_status;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.movie.MovieMapper;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

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
