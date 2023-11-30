package sang.se.bookingmovie.app.room;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.cinema.CinemaEntity;
import sang.se.bookingmovie.app.cinema.CinemaRepository;
import sang.se.bookingmovie.app.room_status.RoomStatusEntity;
import sang.se.bookingmovie.app.room_status.RoomStatusRepository;
import sang.se.bookingmovie.app.seat_room.SeatRoomMapper;
import sang.se.bookingmovie.app.seat_room.SeatRoomService;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.DataNotFoundException;
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

    private final RoomStatusRepository roomStatusRepository;

    private final SeatRoomService seatRoomService;

    private final SeatRoomMapper seatRoomMapper;

    @Override
    public String create(RoomReq roomRequest, String cinemaId) {
        validator.validate(roomRequest);
        CinemaEntity cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new AllException("Not found", 404, List.of("Not found id" + cinemaId)));
        RoomEntity newRoom = new RoomEntity(roomRequest.getName(), roomRequest.getTotalSeats(), roomRequest.getTotalSeats());
        newRoom.setCinema(cinema);
        newRoom.setId(createRoomID());
        newRoom.setSlug(applicationUtil.toSlug(newRoom.getName()));
        newRoom.setStatus(roomStatusRepository.findById(1).orElseThrow());
        roomRepository.save(newRoom);
        return "success";
    }

    @Override
    public ListResponse getAll() {
        List<RoomEntity> roomEntities = roomRepository.findAll();
        return ListResponse.builder()
                .total(roomEntities.size())
                .data(roomEntities.stream()
                        .peek(roomEntity -> {
                            roomEntity.setShowtimes(null);
                            roomEntity.setCinema(null);
                        })
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ListResponse getAllByCinema(String cinemaId) {
        cinemaRepository.findById(cinemaId).orElseThrow(() -> new AllException("Not found", 404, List.of(cinemaId + " not found")));
        List<RoomEntity> roomEntities = roomRepository.findAllByCinemaId(cinemaId);
        return ListResponse.builder()
                .total(roomEntities.size())
                .data(roomEntities.stream()
                        .peek(roomEntity -> roomEntity.setShowtimes(null))
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ListResponse getAllByName(String cinemaId, String name, Integer page, Integer size) {
        cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new AllException("Not found", 404, List.of(cinemaId + " not found")));
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id"));
        Page<RoomEntity> roomEntities = roomRepository.findByNameInCinema(cinemaId, name, pageable);
        return ListResponse.builder()
                .total(roomEntities.getTotalPages())
                .data(roomEntities.stream()
                        .peek(roomEntity -> roomEntity.setShowtimes(null))
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public String updateStatusOfRoom(String roomId, Integer statusId) {
        RoomEntity roomEntity = roomRepository.findById(roomId)
                .orElseThrow(() -> new AllException("NOT FOUND", 404, List.of("Not found room id" + roomId)));
        RoomStatusEntity roomStatusEntity = roomStatusRepository.findById(statusId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Data not found",
                        List.of("status with id " + statusId + " is not exist")));
        roomEntity.setStatus(roomStatusEntity);
        return "Success";
    }

    @Override
    public void createWithCinema(RoomReq roomRequest, CinemaEntity cinema) {
        RoomEntity newRoom = RoomEntity.builder()
                .name(roomRequest.getName())
                .totalSeats(roomRequest.getTotalSeats())
                .availableSeats(roomRequest.getTotalSeats())
                .build();
        newRoom.setCinema(cinema);
        newRoom.setId(createRoomID());
        newRoom.setSlug(applicationUtil.toSlug(newRoom.getName()));
        newRoom.setStatus(roomStatusRepository.findById(1).orElseThrow());
        seatRoomService.createWithRoomEntity(roomRequest.getSeats(), roomRepository.save(newRoom));
    }

    private String createRoomID() {
        long count = roomRepository.count() + 1;
        return "Room" + applicationUtil.addZeros(count, 3);
    }

    private String createRoomID(int index) {
        long count = roomRepository.count() + 1 + index;
        return "Room" + applicationUtil.addZeros(count, 3);
    }
}
