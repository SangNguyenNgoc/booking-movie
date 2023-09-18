package sang.se.bookingmovie.app.ticket;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.bill.BillEntity;
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

    private Double discount;

    private Integer total;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (
            name = "bill_id",
            referencedColumnName = "bill_id",
            nullable = false
    )
    private BillEntity bill;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "showtime_id",
            referencedColumnName = "showtime_id",
            nullable = false
    )
    private ShowtimeEntity showtime;
}
