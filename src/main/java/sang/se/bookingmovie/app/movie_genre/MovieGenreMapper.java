package sang.se.bookingmovie.app.movie_genre;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.validate.ObjectsValidator;

@Service
@RequiredArgsConstructor
public class MovieGenreMapper {
    private final ModelMapper mapper;

    private final ObjectsValidator<MovieGenre> validator;

    public MovieGenreEntity requestToEntity(MovieGenre movieGenre) {
        validator.validate(movieGenre);
        return mapper.map(movieGenre, MovieGenreEntity.class);
    }

    public MovieGenre entityToResponse(MovieGenreEntity movieGenreEntity) {
        return mapper.map(movieGenreEntity, MovieGenre.class);
    }
}
