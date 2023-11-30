package sang.se.bookingmovie.app.seat_type;

import sang.se.bookingmovie.response.ListResponse;

public interface ISeatTypeService {
    String create(SeatType seatType);

    ListResponse getAll();
}
