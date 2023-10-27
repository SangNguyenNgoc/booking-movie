package sang.se.bookingmovie.app.ticket;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.bill.BillEntity;
import sang.se.bookingmovie.app.seat_room.SeatRoomEntity;
import sang.se.bookingmovie.app.showtime.ShowtimeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "tickets")
public class TicketEntity {
    @Id
    @Column(name = "ticket_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn (
            name = "bill_id",
            referencedColumnName = "bill_id",
            nullable = false
    )
    private BillEntity bill;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "showtime_id",
            referencedColumnName = "showtime_id",
            nullable = false
    )
    private ShowtimeEntity showtime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "seat_room",
            referencedColumnName = "seat_room_id",
            nullable = false
    )
    private SeatRoomEntity seatRoom;
}
