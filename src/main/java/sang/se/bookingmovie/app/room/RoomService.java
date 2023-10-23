package sang.se.bookingmovie.app.room;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.cinema.CinemaEntity;
import sang.se.bookingmovie.app.cinema.CinemaRepository;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;
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

    private final ApplicationUtil applicationUtil;

    @Override
    public String create(RoomReq roomRequest, String cinemaId) {
        validator.validate(roomRequest);
        CinemaEntity cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new AllException("Not found", 404, List.of("Not found id" + cinemaId)));
        RoomEntity newRoom =  new RoomEntity(roomRequest.getName(), roomRequest.getTotalSeats(), roomRequest.getTotalSeats());
        newRoom.setCinema(cinema);
        newRoom.setId(createRoomID());
        roomRepository.save(newRoom);
        return "success";
    }

    @Override
    public ListResponse getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id"));
        Page<RoomEntity> roomEntities = roomRepository.findAll(pageable);
        return ListResponse.builder()
                .total(roomEntities.getTotalPages())
                .data(roomEntities.stream()
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ListResponse getAllByCinema(String cinemaId, Integer page, Integer size) {
        cinemaRepository.findById(cinemaId).orElseThrow(() -> new AllException("Not found", 404, List.of(cinemaId + " not found")));
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id"));
        Page<RoomEntity> roomEntities = roomRepository.findAllByCinemaId(cinemaId, pageable);
        return ListResponse.builder()
                .total(roomEntities.getTotalPages())
                .data(roomEntities.stream()
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ListResponse getAllByName(String cinemaId, String name, Integer page, Integer size) {
        cinemaRepository.findById(cinemaId).orElseThrow(() -> new AllException("Not found", 404, List.of(cinemaId + " not found")));
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id"));
        Page<RoomEntity> roomEntities = roomRepository.findByNameInCinema(cinemaId, name, pageable);
        return ListResponse.builder()
                .total(roomEntities.getTotalPages())
                .data(roomEntities.stream()
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private String createRoomID(){
        Long count = roomRepository.count() + 1;
        return "Room" + applicationUtil.addZeros(count,3);
    }
}
