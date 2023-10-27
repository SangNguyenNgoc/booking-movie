package sang.se.bookingmovie.app.bill;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.bill_status.BillStatusEntity;
import sang.se.bookingmovie.app.ticket.TicketEntity;
import sang.se.bookingmovie.app.user.UserEntity;

import java.time.LocalDateTime;
import java.util.Date;
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

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "payment_at")
    private LocalDateTime paymentAt;

    @Column(name = "change_point")
    private Integer changedPoint;

    private Double total;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "cancel_reason")
    private String cancelReason;

    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;

    @OneToMany(
            mappedBy = "bill",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Set<TicketEntity> tickets ;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "user_id"
    )
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "status_id",
            nullable = false,
            referencedColumnName = "bill_status_id"
    )
    private BillStatusEntity status;


}
