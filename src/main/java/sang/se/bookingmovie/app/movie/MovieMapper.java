package sang.se.bookingmovie.app.movie;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.comment.CommentMapper;
import sang.se.bookingmovie.app.format.FormatMapper;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;
import sang.se.bookingmovie.app.movie_genre.MovieGenreMapper;
import sang.se.bookingmovie.app.movie_img.MovieImage;
import sang.se.bookingmovie.app.movie_img.MovieImageMapper;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusMapper;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieMapper implements IMapper<MovieEntity, Movie, MovieResponse> {

    private final ModelMapper mapper;

    private final ObjectsValidator<Movie> validator;

    @Override
    public MovieEntity requestToEntity(Movie movie) {
        validator.validate(movie);
        return null;
    }

    @Override
    public MovieResponse entityToResponse(MovieEntity movieEntity) {
        return  mapper.map(movieEntity, MovieResponse.class);
    }
}
