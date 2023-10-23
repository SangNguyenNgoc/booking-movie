package sang.se.bookingmovie.app.room;

import sang.se.bookingmovie.response.ListResponse;

public interface IRoomService {
    String create(RoomReq roomReqRequest, String cinemaId);

    ListResponse getAll(Integer page, Integer size);

    ListResponse getAllByCinema(String cinemaId, Integer page, Integer size);

    ListResponse getAllByName(String cinemaId, String name, Integer page, Integer size);
}
