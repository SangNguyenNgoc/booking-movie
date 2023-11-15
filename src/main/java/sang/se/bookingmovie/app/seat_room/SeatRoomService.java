package sang.se.bookingmovie.app.seat_room;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        List<SeatRoomEntity> seatRoomEntities = new ArrayList<>();
        seatRoomRequest.stream().map(seatRoomMapper::requestToEntity).forEach(seatRoomEntities::add);
        seatRoomEntities.forEach(e->{
            e.setRoom(roomEntity);
            e.setStatus(true);
            e.setId(null);
        });
        seatRoomEntities.forEach(seatRoomRepository::save);
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

    @Override
    public void createWithRoomEntity(List<SeatRoomRequest> seatRoomRequest, RoomEntity room) {
        List<SeatRoomEntity> seatRoomEntities = seatRoomRequest.stream()
                .map(seatRoomMapper::requestToEntity)
                .peek(seatRoomEntity-> {
                    seatRoomEntity.setRoom(room);
                    seatRoomEntity.setStatus(true);
                    seatRoomEntity.setId(null);
                })
                .toList();
        seatRoomRepository.saveAll(seatRoomEntities);
    }

    private String createSeatRoomID(){
        long count = seatRoomRepository.count() + 1;
        return applicationUtil.addZeros(count,3);
    }
}
