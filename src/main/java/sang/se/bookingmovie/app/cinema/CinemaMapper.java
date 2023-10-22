package sang.se.bookingmovie.app.cinema;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

@Service
@RequiredArgsConstructor
public class CinemaMapper implements IMapper<CinemaEntity, Cinema, CinemaResponse> {
    private final ModelMapper mapper;

    private final ObjectsValidator<Cinema> validator;
    @Override
    public CinemaEntity requestToEntity(Cinema cinema) {
        validator.validate(cinema);
        return mapper.map(cinema, CinemaEntity.class);
    }

    @Override
    public CinemaResponse entityToResponse(CinemaEntity cinemaEntity) {
        return mapper.map(cinemaEntity, CinemaResponse.class);
    }

    public Cinema entityToCinema(CinemaEntity cinemaEntity) {
        return mapper.map(cinemaEntity, Cinema.class);
    }
}
