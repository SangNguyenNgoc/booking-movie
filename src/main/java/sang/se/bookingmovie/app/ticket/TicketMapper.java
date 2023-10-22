package sang.se.bookingmovie.app.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;

@Service
@RequiredArgsConstructor
public class TicketMapper implements IMapper<TicketEntity, Ticket, TicketResponse> {
    @Override
    public TicketEntity requestToEntity(Ticket ticket) {
        return null;
    }

    @Override
    public TicketResponse entityToResponse(TicketEntity ticketEntity) {
        return null;
    }
}
