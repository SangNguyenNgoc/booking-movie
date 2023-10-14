package sang.se.bookingmovie.app.seat_type;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.seat_room.SeatRoomRequest;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

@Service
@RequiredArgsConstructor
public class SeatTypeMapper implements IMapper<SeatTypeEntity, SeatType, SeatType> {
    private final ModelMapper mapper;
    private final ObjectsValidator<SeatType> validator;

    @Override
    public SeatTypeEntity requestToEntity(SeatType seatType) {
//        validator.validate(seatType);
        return mapper.map(seatType, SeatTypeEntity.class);
    }

    @Override
    public SeatType entityToResponse(SeatTypeEntity seatTypeEntity) {
        return mapper.map(seatTypeEntity, SeatType.class);
    }
}
