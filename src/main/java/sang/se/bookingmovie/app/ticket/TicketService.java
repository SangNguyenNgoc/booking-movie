package sang.se.bookingmovie.app.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.bill.BillEntity;
import sang.se.bookingmovie.app.bill.BillRepository;
import sang.se.bookingmovie.app.user.UserEntity;
import sang.se.bookingmovie.app.user.UserRepository;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.exception.UserNotFoundException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.JwtService;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    private final BillRepository billRepository;

    private final JwtService jwtService;

    private final TicketMapper ticketMapper;

    @Override
    public ListResponse getTicketInUser(String token, Boolean stillValid) {
        String userId = jwtService.extractSubject(jwtService.validateToken(token));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Conflict", List.of("User is not exits")));
        List<TicketEntity> ticketEntities;
        List<TicketResponse> ticketResponses;
        if(stillValid) {
            ticketEntities = ticketRepository.findByUser(userEntity.getId());
            ticketResponses = ticketEntities.stream()
                    .filter(ticketEntity -> ticketMapper.checkTicketValid(ticketEntity.getShowtime()))
                    .peek(ticketEntity -> ticketEntity.setStillValid(true))
                    .map(ticketMapper::entityToResponseList)
                    .toList();
        } else {
            ticketEntities = ticketRepository.findByUser(userEntity.getId());
            ticketResponses = ticketEntities.stream()
                    .filter(ticketEntity -> !ticketMapper.checkTicketValid(ticketEntity.getShowtime()))
                    .peek(ticketEntity -> ticketEntity.setStillValid(false))
                    .map(ticketMapper::entityToResponseList)
                    .toList();
        }
        return ListResponse.builder()
                .total(ticketResponses.size())
                .data(ticketResponses)
                .build();
    }

    @Override
    public ListResponse getTicketInBill(String token, String billId) {
        String userId = jwtService.extractSubject(jwtService.validateToken(token));
        BillEntity billEntity = billRepository.findById(billId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Bill is not exits")));
        if (!billEntity.getUser().getId().equals(userId)) {
            throw new AllException("Access denied", 403, List.of("Access denied"));
        }
        List<TicketEntity> ticketEntities = ticketRepository.findByBill(billId);
        List<TicketResponse> ticketResponses = ticketEntities.stream()
                .peek(ticketEntity -> ticketEntity.setStillValid(ticketMapper.checkTicketValid(ticketEntity.getShowtime())))
                .map(ticketMapper::entityToResponseList)
                .toList();
        return ListResponse.builder()
                .total(ticketResponses.size())
                .data(ticketResponses)
                .build();
    }

    @Override
    public TicketResponse getDetail(String token, String ticketId) {
        String userId = jwtService.extractSubject(jwtService.validateToken(token));
        TicketEntity ticketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Ticket is not exits")));
        if(!ticketEntity.getBill().getUser().getId().equals(userId)) {
            throw new AllException("Access denied", 403, List.of("Access denied"));
        }
        return ticketMapper.entityToResponse(ticketEntity);
    }

}
