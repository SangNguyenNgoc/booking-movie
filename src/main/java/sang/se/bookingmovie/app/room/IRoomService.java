package sang.se.bookingmovie.app.room;

import sang.se.bookingmovie.response.ListResponse;

public interface IRoomService {
    RoomReq create(RoomReq roomReqRequest, String cinemaId);

    ListResponse getAll();
}
