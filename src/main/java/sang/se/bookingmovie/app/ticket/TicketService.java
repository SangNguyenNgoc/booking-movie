package sang.se.bookingmovie.app.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
}
