package sang.se.bookingmovie.app.bill;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.seat_room.SeatRoomEntity;
import sang.se.bookingmovie.app.seat_room.SeatRoomRepository;
import sang.se.bookingmovie.app.showtime.ShowtimeEntity;
import sang.se.bookingmovie.app.showtime.ShowtimeRepository;
import sang.se.bookingmovie.app.ticket.TicketEntity;
import sang.se.bookingmovie.app.ticket.TicketRepository;
import sang.se.bookingmovie.app.user.UserEntity;
import sang.se.bookingmovie.app.user.UserRepository;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.UserNotFoundException;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.JwtService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BillService implements IBillService {

    @Value("${promotion.point}")
    private Integer promoPoint;

    @Value("${promotion.promo}")
    private Integer promo;

    private final BillRepository billRepository;

    private final ShowtimeRepository showtimeRepository;

    private final TicketRepository ticketRepository;

    private final SeatRoomRepository seatRoomRepository;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final ApplicationUtil applicationUtil;

    @Override
    @Transactional
    public String create(String token, Bill bill) {
        checkSeat(bill.getShowtimeId(), bill.getSeatId());
        String userId = jwtService.extractSubject(jwtService.validateToken(token));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found", List.of("User is not exits")));
        if(userEntity.getPoint() >= bill.getChangedPoint()) {
            userEntity.setPoint(userEntity.getPoint() - bill.getChangedPoint());
        } else {
            throw new AllException("Not Enough Points", 404, List.of("Not Enough Points"));
        }
        ShowtimeEntity showtimeEntity = showtimeRepository.findById(bill.getShowtimeId())
                .orElseThrow(() -> new UserNotFoundException("Data not found", List.of("Showtime is not exits")));
        List<SeatRoomEntity> seatRoomEntities = seatRoomRepository.findAllById(bill.getSeatId());
        Set<TicketEntity> ticketEntities = new HashSet<>();
        BillEntity billEntity = BillEntity.builder()
                .id(applicationUtil.createUUID())
                .changedPoint(bill.getChangedPoint())
                .paymentAt(null)
                .status("Unpaid")
                .cancelReason(null)
                .total(seatRoomEntities.stream().mapToDouble(this::getPriceOfSeat).sum() - bill.getChangedPoint() * promo)
                .user(userEntity)
                .build();
        seatRoomEntities.forEach(seatRoomEntity -> {
            TicketEntity ticketEntity = TicketEntity.builder()
                    .id(applicationUtil.createUUID(showtimeEntity.getId() + "-" + seatRoomEntity.getId()))
                    .seatRoom(seatRoomEntity)
                    .showtime(showtimeEntity)
                    .bill(billEntity)
                    .build();
            ticketEntities.add(ticketEntity);
        });
        billEntity.setTransactionId(applicationUtil.createUUID(billEntity.getId()));
        billEntity.setTickets(ticketEntities);
        billRepository.save(billEntity);
        userEntity.setPoint(userEntity.getPoint() + promoPoint);
        return "Success";
    }

    private Double getPriceOfSeat(SeatRoomEntity seatRoomEntity) {
        return seatRoomEntity.getType().getPrice();
    }

    private void checkSeat(String showtimeId, List<Integer> seatIds) {
        seatIds.forEach(seatId -> {
            if(ticketRepository.findByShowtime(showtimeId, seatId).size() != 0) {
                throw new AllException("Seat already reserved", 400, List.of("Seat already reserved"));
            }
        });
    }

}
