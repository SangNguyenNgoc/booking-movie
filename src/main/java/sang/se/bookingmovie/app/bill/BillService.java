package sang.se.bookingmovie.app.bill;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.bill_status.BillStatusEntity;
import sang.se.bookingmovie.app.bill_status.BillStatusRepository;
import sang.se.bookingmovie.app.seat_room.SeatRoomEntity;
import sang.se.bookingmovie.app.seat_room.SeatRoomRepository;
import sang.se.bookingmovie.app.showtime.ShowtimeEntity;
import sang.se.bookingmovie.app.showtime.ShowtimeRepository;
import sang.se.bookingmovie.app.ticket.TicketEntity;
import sang.se.bookingmovie.app.ticket.TicketRepository;
import sang.se.bookingmovie.app.user.UserEntity;
import sang.se.bookingmovie.app.user.UserRepository;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.exception.UserNotFoundException;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.JwtService;
import sang.se.bookingmovie.vnpay.VnpayService;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    private final BillStatusRepository billStatusRepository;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final ApplicationUtil applicationUtil;

    private final VnpayService vnpayService;

    @Override
    @Transactional
    public String create(String token, Bill bill) throws UnsupportedEncodingException {
        checkSeat(bill.getShowtimeId(), bill.getSeatId());
        String userId = jwtService.extractSubject(jwtService.validateToken(token));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found", List.of("User is not exits")));
        setPointToUser(userEntity, bill.getChangedPoint());

        ShowtimeEntity showtimeEntity = showtimeRepository.findById(bill.getShowtimeId())
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Showtime is not exits")));
        BillStatusEntity billStatusEntity = billStatusRepository.findById(1)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Bill status is not exits")));
        List<SeatRoomEntity> seatRoomEntities = seatRoomRepository.findAllById(bill.getSeatId());

        String billId = applicationUtil.createUUID();
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        BillEntity billEntity = BillEntity.builder()
                .id(billId)
                .transactionId(applicationUtil.createUUID(billId))
                .changedPoint(bill.getChangedPoint())
                .paymentAt(null)
                .status(billStatusEntity)
                .createTime(cld.getTime())
                .cancelReason(null)
                .total(seatRoomEntities.stream().mapToDouble(this::getPriceOfSeat).sum() - bill.getChangedPoint() * promo)
                .user(userEntity)
                .build();
        billEntity.setTickets(createTicket(showtimeEntity, seatRoomEntities, billEntity));
        billRepository.save(billEntity);
        return vnpayService.doPost(billEntity);
    }

    private Double getPriceOfSeat(SeatRoomEntity seatRoomEntity) {
        return seatRoomEntity.getType().getPrice();
    }

    private void checkSeat(String showtimeId, List<Integer> seatIds) {
        seatIds.forEach(seatId -> {
            if(!ticketRepository.findByShowtime(showtimeId, seatId).isEmpty()) {
                throw new AllException("Seat already reserved", 400, List.of("Seat already reserved"));
            }
        });
    }

    private void setPointToUser(UserEntity userEntity, Integer promo) {
        if(promo > 0) {
            if(userEntity.getPoint() >= promo) {
                userEntity.setPoint(userEntity.getPoint() - promo);
            } else {
                throw new AllException("Not Enough Points", 404, List.of("Not Enough Points"));
            }
        } else {
            userEntity.setPoint(userEntity.getPoint() + promoPoint);
        }
    }

    private Set<TicketEntity> createTicket(
            ShowtimeEntity showtimeEntity,
            List<SeatRoomEntity> seatRoomEntities,
            BillEntity billEntity
    ) {
        return seatRoomEntities.stream()
                .map(seatRoomEntity -> TicketEntity.builder()
                        .id(applicationUtil.createUUID(showtimeEntity.getId() + "-" + seatRoomEntity.getId()))
                        .seatRoom(seatRoomEntity)
                        .showtime(showtimeEntity)
                        .bill(billEntity)
                        .build())
                .collect(Collectors.toSet());
    }

}
