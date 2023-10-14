package sang.se.bookingmovie.app.showtime;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

@Service
@RequiredArgsConstructor
public class ShowtimeMapper implements IMapper<ShowtimeEntity, ShowtimeRequest, ShowtimeResponse> {

    private final ModelMapper mapper;
    private final ObjectsValidator<ShowtimeRequest> validator;

    @Override
    public ShowtimeEntity requestToEntity(ShowtimeRequest showtimeRequest) {
        validator.validate(showtimeRequest);
        return mapper.map(showtimeRequest, ShowtimeEntity.class);
    }

    @Override
    public ShowtimeResponse entityToResponse(ShowtimeEntity showtimeEntity) {
        return null;
    }
}
