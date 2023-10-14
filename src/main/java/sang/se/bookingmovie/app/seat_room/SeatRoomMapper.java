package sang.se.bookingmovie.app.seat_room;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

@Service
@RequiredArgsConstructor
public class SeatRoomMapper implements IMapper<SeatRoomEntity, SeatRoomRequest, SeatRoomResponse> {
    private final ModelMapper mapper;
    private final ObjectsValidator<SeatRoomRequest> validator;

    @Override
    public SeatRoomEntity requestToEntity(SeatRoomRequest seatRoomRequest) {
        validator.validate(seatRoomRequest);
        return mapper.map(seatRoomRequest, SeatRoomEntity.class);
    }

    @Override
    public SeatRoomResponse entityToResponse(SeatRoomEntity seatRoomEntity) {
        return mapper.map(seatRoomEntity, SeatRoomResponse.class);
    }
}
