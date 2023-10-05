package sang.se.bookingmovie.app.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.cinema.CinemaEntity;
import sang.se.bookingmovie.app.cinema.CinemaRepository;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final ObjectsValidator<RoomReq> validator;

    private final RoomRepository roomRepository;

    private final CinemaRepository cinemaRepository;

    private final RoomMapper mapper;

    @Override
    public RoomReq create(RoomReq roomRequest, String cinemaId) {
        validator.validate(roomRequest);
        CinemaEntity cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new AllException("Not found", 404, List.of("Not found id" + cinemaId)));
        RoomEntity newRoom =  new RoomEntity(roomRequest.getId(), roomRequest.getTotalSeats(), roomRequest.getTotalSeats());
        newRoom.setCinema(cinema);

        return mapper.entityToResponse(roomRepository.save(newRoom));
    }

    @Override
    public ListResponse getAll() {
        List<RoomEntity> roomEntities = roomRepository.findAll();
        return ListResponse.builder()
                .total(roomEntities.size())
                .data(roomEntities.stream()
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
