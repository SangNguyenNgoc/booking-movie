package sang.se.bookingmovie.app.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.comment.CommentMapper;
import sang.se.bookingmovie.app.format.FormatMapper;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;
import sang.se.bookingmovie.app.movie_genre.MovieGenreMapper;
import sang.se.bookingmovie.app.movie_img.MovieImage;
import sang.se.bookingmovie.app.movie_img.MovieImageMapper;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusMapper;
import sang.se.bookingmovie.app.showtime.ShowtimeEntity;
import sang.se.bookingmovie.app.showtime.ShowtimeMapper;
import sang.se.bookingmovie.app.showtime.ShowtimeResponse;
import sang.se.bookingmovie.exception.JsonException;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieMapper implements IMapper<MovieEntity, Movie, MovieResponse> {

    private final ModelMapper mapper;

    private final ShowtimeMapper showtimeMapper;

    private final ObjectMapper objectMapper;

    private final ObjectsValidator<Movie> validator;

    @Override
    public MovieEntity requestToEntity(Movie movie) {
        validator.validate(movie);
        return mapper.map(movie, MovieEntity.class);
    }

    @Override
    public MovieResponse entityToResponse(MovieEntity movieEntity) {
        if(movieEntity.getShowtimes() == null) {
            return mapper.map(movieEntity, MovieResponse.class);
        } else {
            MovieResponse movieResponse = mapper.map(movieEntity, MovieResponse.class);
            movieResponse.setShowtimes(movieEntity.getShowtimes().stream()
                    .map(showtimeMapper::entityToResponse)
                    .collect(Collectors.toList())
            );
            return movieResponse;
        }
    }

    public MovieEntity jsonToEntity(String movieJson) {
        try {
            Movie movie = objectMapper.readValue(movieJson, Movie.class);
            return requestToEntity(movie);
        } catch (JsonProcessingException e) {
            throw new JsonException("Json error", List.of("Parse json failure"));
        }
    }


}
