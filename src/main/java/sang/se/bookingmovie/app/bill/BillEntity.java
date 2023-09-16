package sang.se.bookingmovie.app.bill;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.ticket.TicketEntity;
import sang.se.bookingmovie.app.user.UserEntity;
import sang.se.bookingmovie.app.user.UserService;

import java.time.LocalDate;
import java.util.Date;
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

    private Integer total;

    @OneToMany(
            mappedBy = "bill",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Set<TicketEntity> tickets ;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "user_id"
    )
    private UserEntity user;


}
