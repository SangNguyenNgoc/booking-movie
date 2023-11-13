package sang.se.bookingmovie.app.seat_room;

import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.response.ListResponse;

import java.util.List;

public interface ISeatRoomService {
    String create(List<SeatRoomRequest> seatRoomRequest, String roomId);

    ListResponse getSeatRoom(String roomId);

    Void createWithRoomEntity(List<SeatRoomRequest> seatRoomRequest, RoomEntity room);
}
