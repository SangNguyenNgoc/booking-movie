package sang.se.bookingmovie.app.cinema;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;
import sang.se.bookingmovie.app.movie_genre.MovieGenreEntity;
import sang.se.bookingmovie.exception.AllException;
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
        cinemaEntity.setSlug(applicationUtil.toSlug(cinemaEntity.getName()));
        cinemaRepository.save(cinemaEntity);
        return "success";
    }

    @Override
    public ListResponse getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id"));
        Page<CinemaEntity> cinemaEntities = cinemaRepository.findAll(pageable);
        return ListResponse.builder()
                .total(cinemaEntities.getTotalPages())
                .data(cinemaEntities.stream()
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Cinema getById(String cinemaId) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(cinemaId)
                .orElseThrow(()-> new AllException("Not Found", 404, List.of("cinemaId not found")));
        return mapper.entityToCinema(cinemaEntity);

    }

    @Override
    public String update(Cinema cinemaRequest, String cinemaId) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(cinemaId)
                .orElseThrow(()-> new AllException("Not Found", 404, List.of("cinemaId not found")));
        update(cinemaEntity, cinemaRequest);
        cinemaRepository.save(cinemaEntity);
        return "success";
    }

    private String createCinemaID(){
        long count = cinemaRepository.count() + 1;
        return "Cinema" + applicationUtil.addZeros(count,3);
    }

    public void update(CinemaEntity cinemaEntity, Cinema cinema){
        cinemaEntity.setName(cinema.getName());
        cinemaEntity.setAddress(cinema.getAddress());
        cinemaEntity.setCity(cinema.getCity());
        cinemaEntity.setDistrict(cinema.getDistrict());
        cinemaEntity.setPhoneNumber(cinema.getPhoneNumber());
    }
}
