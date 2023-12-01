package sang.se.bookingmovie.app.cinema;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.room.RoomResponse;
import sang.se.bookingmovie.app.room.RoomService;
import sang.se.bookingmovie.app.showtime.ShowtimeEntity;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaService implements ICinemaService {
    private final CinemaRepository cinemaRepository;

    private final CinemaMapper mapper;

    private final ApplicationUtil applicationUtil;

    private final RoomService roomService;

    @Override
    public String create(Cinema cinemaRequest) {
        CinemaEntity cinemaEntity = mapper.requestToEntity(cinemaRequest);
        cinemaEntity.setId(createCinemaID());
        cinemaEntity.setSlug(applicationUtil.toSlug(cinemaEntity.getName()));
        cinemaEntity.setStatus(getStatusInRequest(cinemaRequest.getStatus()));
        cinemaRepository.save(cinemaEntity);
        return "success";
    }

    @Override
    public ListResponse getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id"));
        Page<CinemaEntity> cinemaEntities = cinemaRepository.findAll(pageable);
        return ListResponse.builder()
                .total(cinemaEntities.getSize())
                .data(cinemaEntities.stream()
                        .peek(cinemaEntity -> cinemaEntity.setRooms(null))
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Cinema getById(String cinemaId) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new AllException("Not Found", 404, List.of("cinemaId not found")));
        cinemaEntity.setRooms(null);
        return mapper.entityToCinema(cinemaEntity);
    }

    @Override
    public CinemaResponse getByIdInAdmin(String cinemaId) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new AllException("Not Found", 404, List.of("cinemaId not found")));
        Set<RoomEntity> roomEntities = cinemaEntity.getRooms().stream()
                .peek(roomEntity -> {
                    roomEntity.setCinema(null);
                    roomEntity.setShowtimes(null);
                    roomEntity.setSeats(null);
                })
                .collect(Collectors.toSet());
        cinemaEntity.setRooms(roomEntities);
        CinemaResponse cinemaResponse = mapper.entityToResponse(cinemaEntity);
        cinemaResponse.setRooms(cinemaResponse.getRooms().stream()
                .sorted(Comparator.comparing(RoomResponse::getId))
                .collect(Collectors.toList())
        );
        return cinemaResponse;

    }

    @Override
    public String update(Cinema cinemaRequest, String cinemaId) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new AllException("Not Found", 404, List.of("cinemaId not found")));
        if (getStatusInRequest(cinemaRequest.getStatus()) != CinemaStatus.OPENING) {
            int showtimeInCinema = cinemaEntity.getRooms().stream()
                    .flatMap(roomEntity -> roomEntity.getShowtimes().stream())
                    .filter(ShowtimeEntity::getStatus)
                    .toList().size();
            if (showtimeInCinema > 0) {
                throw new AllException("Bad request",
                        400,
                        List.of("It is not possible to close or maintain this cinema because screenings still exist")
                );
            }
        }
        update(cinemaEntity, cinemaRequest);
        cinemaRepository.save(cinemaEntity);
        return "Success";
    }

    @Override
    public String createCinemaWithRoom(CinemaRequest cinemaRequest) {
        CinemaEntity cinemaEntity = mapper.cinemaRequestToEntity(cinemaRequest);
        cinemaEntity.setId(createCinemaID());
        cinemaEntity.setSlug(applicationUtil.toSlug(cinemaEntity.getName()));
        cinemaEntity.setRooms(null);
        cinemaEntity.setStatus(getStatusInRequest(cinemaRequest.getStatus()));
        cinemaRepository.save(cinemaEntity);
        cinemaRequest.getRooms().forEach(roomReq -> roomService.createWithCinema(roomReq, cinemaEntity));
        return "success";
    }

    private String createCinemaID() {
        long count = cinemaRepository.count() + 1;
        return "Cinema" + applicationUtil.addZeros(count, 3);
    }

    private void update(CinemaEntity cinemaEntity, Cinema cinema) {
        cinemaEntity.setName(cinema.getName());
        cinemaEntity.setAddress(cinema.getAddress());
        cinemaEntity.setCity(cinema.getCity());
        cinemaEntity.setDistrict(cinema.getDistrict());
        cinemaEntity.setPhoneNumber(cinema.getPhoneNumber());
        cinemaEntity.setStatus(getStatusInRequest(cinema.getStatus()));
    }

    private CinemaStatus getStatusInRequest(String input) {
        switch (input) {
            case "Hoạt động" -> {
                return CinemaStatus.OPENING;
            }
            case "Đóng cửa" -> {
                return CinemaStatus.CLOSED;
            }
            case "Đang bảo trì" -> {
                return CinemaStatus.MAINTAINED;
            }
            default -> throw new AllException("Data invalid", 404, List.of("Cinema status invalid"));
        }
    }
}
