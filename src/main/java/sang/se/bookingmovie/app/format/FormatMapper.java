package sang.se.bookingmovie.app.format;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.movie.MovieMapper;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormatMapper implements IMapper<FormatEntity, Format, Format> {

    private final ModelMapper mapper;

    private final ObjectsValidator<Format> validator;

    @Override
    public FormatEntity requestToEntity(Format format) {
        validator.validate(format);
        return mapper.map(format, FormatEntity.class);
    }

    @Override
    public Format entityToResponse(FormatEntity formatEntity) {
        return mapper.map(formatEntity, Format.class);
    }
}
