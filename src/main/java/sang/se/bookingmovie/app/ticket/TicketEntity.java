package sang.se.bookingmovie.app.ticket;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.bill.BillEntity;

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

    @ManyToOne
    @JoinColumn (
            name = "bill_id",
            nullable = false,
            referencedColumnName = "bill_id"
    )
    private BillEntity bill;
}
