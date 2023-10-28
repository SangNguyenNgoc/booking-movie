package sang.se.bookingmovie.app.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.comment.CommentEntity;
import sang.se.bookingmovie.app.comment.CommentMapper;
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

    private final ObjectMapper objectMapper;

    private final ObjectsValidator<Movie> validator;

    @Override
    public MovieEntity requestToEntity(Movie movie) {
        validator.validate(movie);
        return mapper.map(movie, MovieEntity.class);
    }

    @Override
    public MovieResponse entityToResponse(MovieEntity movieEntity) {
        if(movieEntity.getComments() == null) {
            return mapper.map(movieEntity, MovieResponse.class);
        } else {
            MovieResponse movieResponse = mapper.map(movieEntity, MovieResponse.class);
            movieResponse.setComment(movieEntity.getComments().stream()
                    .sorted(Comparator.comparing(CommentEntity::getCreateDate).reversed())
                    .filter(CommentEntity::getStatus)
                    .map(commentMapper::entityToResponse)
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
