package sang.se.bookingmovie.app.cinema;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;
import sang.se.bookingmovie.app.movie_genre.MovieGenreEntity;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaService implements ICinemaService {
    private final CinemaRepository cinemaRepository;

    private final CinemaMapper mapper;

    private final ApplicationUtil applicationUtil;

    @Override
    public String create(Cinema cinemaRequest) {
        CinemaEntity cinemaEntity = mapper.requestToEntity(cinemaRequest);
        cinemaEntity.setId(createCinemaID());
        cinemaRepository.save(cinemaEntity);
        return "success";
    }

    @Override
    public ListResponse getAll() {
        List<CinemaEntity> cinemaEntities = cinemaRepository.findAll();
        return ListResponse.builder()
                .total(cinemaEntities.size())
                .data(cinemaEntities.stream()
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private String createCinemaID(){
        Long count = cinemaRepository.count() + 1;
        return "Cinema" + applicationUtil.addZeros(count,3);
    }
}
