package sang.se.bookingmovie.app.seat_room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.room.RoomRepository;
import sang.se.bookingmovie.app.seat_type.SeatTypeRepository;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatRoomService implements ISeatRoomService {
    private final SeatRoomRepository seatRoomRepository;
    private final RoomRepository roomRepository;
    private final SeatRoomMapper seatRoomMapper;
    private final SeatTypeRepository seatTypeRepository;
    private final ApplicationUtil applicationUtil;


    @Override
    public String create(List<SeatRoomRequest> seatRoomRequest, String roomId) {
        RoomEntity roomEntity = roomRepository.findById(roomId)
                .orElseThrow(()->new AllException("Not found", 404, List.of("Not found room_id")));
//        System.out.println(roomEntity.getAvailableSeats());
        List<SeatRoomEntity> seatRoomEntities = new ArrayList<>();
        seatRoomRequest.stream().map(seatRoomMapper::requestToEntity).forEach(seatRoomEntities::add);
        seatRoomEntities.forEach(e->e.setRoom(roomEntity));
        seatRoomRepository.saveAll(seatRoomEntities);
        return "success";
    }

    @Override
    public ListResponse getSeatRoom(String roomId) {
        List<SeatRoomEntity> seatRoomEntities = seatRoomRepository.findAllByRoomId(roomId);
        return ListResponse.builder()
                .total(seatRoomEntities.size())
                .data(seatRoomEntities.stream()
                        .map(seatRoomMapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private String createSeatRoomID(){
        Long count = seatRoomRepository.count() + 1;
        return applicationUtil.addZeros(count,3);
    }
}
