package sang.se.bookingmovie.app.user;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.bill.BillEntity;
import sang.se.bookingmovie.app.role.RoleEntity;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private String id;

    private String username;

    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(
            name = "role_id",
            nullable = false,
            referencedColumnName = "role_id"
    )
    private RoleEntity role;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Set<BillEntity> bills;
}
