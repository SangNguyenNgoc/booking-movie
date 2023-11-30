package sang.se.bookingmovie.app.ticket;

import sang.se.bookingmovie.response.ListResponse;

public interface ITicketService {

    ListResponse getTicketInUser(String token, Boolean stillValid);

    ListResponse getTicketInBill(String token, String billId);

    TicketResponse getDetail(String token, String ticketId);
}
