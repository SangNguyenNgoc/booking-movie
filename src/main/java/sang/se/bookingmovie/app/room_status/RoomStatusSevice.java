package sang.se.bookingmovie.app.room_status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.response.ListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomStatusSevice implements IRoomStatusService{
    private final RoomStatusRepository roomStatusRepository;
    @Override
    public ListResponse getAll() {
        List<RoomStatusEntity> roomStatusEntities = roomStatusRepository.findAll();
        return ListResponse.builder()
                .total(roomStatusEntities.size())
                .data(roomStatusEntities)
                .build();
    }
}
