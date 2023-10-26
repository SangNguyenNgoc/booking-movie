package sang.se.bookingmovie.app.room;

import sang.se.bookingmovie.response.ListResponse;

public interface IRoomService {
    String create(RoomReq roomReqRequest, String cinemaId);

    ListResponse getAll();

    ListResponse getAllByCinema(String cinemaId);

    ListResponse getAllByName(String cinemaId, String name, Integer page, Integer size);

    String updateStatusOfRoom(String roomId, Integer statusId);
}
