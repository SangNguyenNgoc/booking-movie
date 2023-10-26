package sang.se.bookingmovie.app.bill_status;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.bill.BillEntity;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "bill_status")
public class BillStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_status_id")
    private Integer id;

    private String name;

    @OneToMany(
            mappedBy = "status",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<BillEntity> bills;
}
