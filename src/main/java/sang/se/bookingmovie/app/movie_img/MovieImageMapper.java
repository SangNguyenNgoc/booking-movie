package sang.se.bookingmovie.app.movie_img;

import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;

@Service
@RequiredArgsConstructor
public class MovieImageMapper implements IMapper<MovieImageEntity, MovieImage, MovieImage> {

    private final ModelMapper mapper;
    @Override
    public MovieImageEntity requestToEntity(MovieImage movieImage) {
        return null;
    }

    @Override
    public MovieImage entityToResponse(MovieImageEntity movieImageEntity) {
        MovieImage movieImage = mapper.map(movieImageEntity, MovieImage.class);
        return null;
    }
}
