package sang.se.bookingmovie.app.room;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

@Service
@RequiredArgsConstructor
public class RoomMapper implements IMapper<RoomEntity, RoomReq, RoomResponse> {
    private final ModelMapper mapper;

    private final ObjectsValidator<RoomReq> validator;

    @Override
    public RoomEntity requestToEntity(RoomReq roomReq) {
        return null;
    }

    @Override
    public RoomResponse entityToResponse(RoomEntity roomEntity) {
        return mapper.map(roomEntity, RoomResponse.class);
    }
}
