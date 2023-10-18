package sang.se.bookingmovie.app.cinema;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;

@Service
@RequiredArgsConstructor
public class CinemaMapper2 implements IMapper<CinemaEntity, Cinema, CinemaResponse> {

    private final ModelMapper mapper;
    @Override
    public CinemaEntity requestToEntity(Cinema cinema) {
        return null;
    }

    @Override
    public CinemaResponse entityToResponse(CinemaEntity cinemaEntity) {
        return mapper.map(cinemaEntity, CinemaResponse.class);
    }
}
