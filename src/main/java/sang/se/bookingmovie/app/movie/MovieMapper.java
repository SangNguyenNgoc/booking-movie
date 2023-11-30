package sang.se.bookingmovie.app.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.comment.CommentEntity;
import sang.se.bookingmovie.app.comment.CommentMapper;
import sang.se.bookingmovie.app.comment.CommentStatus;
import sang.se.bookingmovie.app.showtime.ShowtimeMapper;
import sang.se.bookingmovie.app.showtime.ShowtimeResponse;
import sang.se.bookingmovie.exception.JsonException;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieMapper implements IMapper<MovieEntity, Movie, MovieResponse> {

    private final ModelMapper mapper;

    private final CommentMapper commentMapper;

    private final ShowtimeMapper showtimeMapper;

    private final ObjectMapper objectMapper;

    private final ObjectsValidator<Movie> validator;

    private final ObjectsValidator<MovieUpdate> validatorUpdate;

    @Override
    public MovieEntity requestToEntity(Movie movie) {
        validator.validate(movie);
        return mapper.map(movie, MovieEntity.class);
    }

    public MovieEntity requestToEntity(MovieUpdate movie) {
        validatorUpdate.validate(movie);
        return mapper.map(movie, MovieEntity.class);
    }

    @Override
    public MovieResponse entityToResponse(MovieEntity movieEntity) {
        if (movieEntity.getComments() == null) {
            return mapper.map(movieEntity, MovieResponse.class);
        } else {
            MovieResponse movieResponse = mapper.map(movieEntity, MovieResponse.class);
            movieResponse.setComment(movieEntity.getComments().stream()
                    .sorted(Comparator.comparing(CommentEntity::getCreateDate).reversed())
                    .filter(commentEntity -> commentEntity.getStatus() == CommentStatus.APPROVED)
                    .map(commentMapper::entityToResponse)
                    .collect(Collectors.toList())
            );
            return movieResponse;
        }
    }

    public MovieResponse entityCinemaDetailShowtime(MovieEntity movieEntity) {
        if (movieEntity.getShowtimes() == null) {
            return mapper.map(movieEntity, MovieResponse.class);
        } else {
            MovieResponse movieResponse = mapper.map(movieEntity, MovieResponse.class);
            movieResponse.setShowtimes(movieResponse.getShowtimes().stream()
                    .sorted(Comparator.comparing(ShowtimeResponse::getStartTime))
                    .peek(showtimeResponse -> {
                        int lastIndex = showtimeResponse.getStartTime().lastIndexOf(":");
                        showtimeResponse.setStartTime(showtimeResponse.getStartTime().substring(0, lastIndex));
                    })
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

    public Movie jsonToRequest(String movieJson) {
        try {
            return objectMapper.readValue(movieJson, Movie.class);
        } catch (JsonProcessingException e) {
            throw new JsonException("Json error", List.of("Parse json failure"));
        }
    }

    public MovieUpdate jsonToRequestUpdate(String movieJson) {
        try {
            return objectMapper.readValue(movieJson, MovieUpdate.class);
        } catch (JsonProcessingException e) {
            throw new JsonException("Json error", List.of("Parse json failure"));
        }
    }


}
