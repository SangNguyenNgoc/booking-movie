package sang.se.bookingmovie.app.ticket;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.seat_room.SeatRoomEntity;
import sang.se.bookingmovie.app.showtime.ShowtimeEntity;
import sang.se.bookingmovie.utils.IMapper;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TicketMapper implements IMapper<TicketEntity, TicketResponse, TicketResponse> {

    private ModelMapper mapper;
    @Override
    public TicketEntity requestToEntity(TicketResponse ticketResponse) {
        return null;
    }

    @Override
    public TicketResponse entityToResponse(TicketEntity ticketEntity) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(ticketEntity.getShowtime().getStartTime());
        sdf = new SimpleDateFormat("EE dd/MM");
        String date = sdf.format(ticketEntity.getShowtime().getStartDate());
        SeatRoomEntity seat = ticketEntity.getSeatRoom();
        return TicketResponse.builder()
                .id(ticketEntity.getId())
                .showtime(date + " " + time)
                .cinema(seat.getRoom().getCinema().getName())
                .movie(ticketEntity.getShowtime().getMovie().getName())
                .stillValid(checkTicketValid(ticketEntity.getShowtime()))
                .seat(seat.getRow() + seat.getRowIndex())
                .price(seat.getType().getPrice())
                .cinemaAddress(seat.getRoom().getCinema().getAddress())
                .room(seat.getRoom().getName())
                .build();
    }

    public TicketResponse entityToResponseList(TicketEntity ticketEntity) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(ticketEntity.getShowtime().getStartTime());
        sdf = new SimpleDateFormat("EE dd/MM");
        String date = sdf.format(ticketEntity.getShowtime().getStartDate());
        SeatRoomEntity seat = ticketEntity.getSeatRoom();
        return TicketResponse.builder()
                .id(ticketEntity.getId())
                .showtime(date + " " + time)
                .cinema(seat.getRoom().getCinema().getName())
                .movie(ticketEntity.getShowtime().getMovie().getName())
                .build();
    }

    public Boolean checkTicketValid(ShowtimeEntity showtimeEntity) {
        if(showtimeEntity.getStartDate().toLocalDate().isBefore(LocalDate.now())) {
            return false;
        } else if (showtimeEntity.getStartDate().toLocalDate().isAfter(LocalDate.now())) {
            return true;
        } else {
            long runningTime = showtimeEntity.getRunningTime();
            LocalTime validTime = showtimeEntity.getStartTime().toLocalTime().plusMinutes(runningTime);
            return validTime.isAfter(LocalTime.now());
        }
    }

}
