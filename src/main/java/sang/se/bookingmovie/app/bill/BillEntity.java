package sang.se.bookingmovie.app.bill;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.ticket.TicketEntity;
import sang.se.bookingmovie.app.user.UserEntity;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "bills")
public class BillEntity {
    @Id
    @Column(name = "bill_id")
    private String id;

    @Column(name = "payment_at")
    private LocalDate paymentAt;

    @Column(name = "change_point")
    private Integer changedPoint;

    private Double total;

    private String status;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "cancel_reason")
    private String cancelReason;

    @OneToMany(
            mappedBy = "bill",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<TicketEntity> tickets ;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "user_id"
    )
    private UserEntity user;


}
