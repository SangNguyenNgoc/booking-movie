package sang.se.bookingmovie.app.movie_genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.response.ListResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieGenreService implements IMovieGenreService {

    private final MovieGenreRepository movieGenreRepository;

    private final MovieGenreMapper mapper;

    @Override
    public String create(MovieGenre movieGenre) {
        MovieGenreEntity movieGenreEntity = mapper.requestToEntity(movieGenre);
        movieGenreRepository.save(movieGenreEntity);
        return "Success";
    }

    @Override
    public ListResponse getAll() {
        List<MovieGenreEntity> movieGenreEntities = movieGenreRepository.findAll();
        return ListResponse.builder()
                .total(movieGenreEntities.size())
                .data(movieGenreEntities.stream()
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }


}
