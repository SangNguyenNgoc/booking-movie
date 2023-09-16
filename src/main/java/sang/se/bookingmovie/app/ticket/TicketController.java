package sang.se.bookingmovie.app.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
}
