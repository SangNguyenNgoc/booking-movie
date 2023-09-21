package sang.se.bookingmovie.app.role;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.user.UserEntity;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @Column(name = "role_id")
    private String id;

    @Column(name = "role_name")
    private String name;

    @OneToMany(
            mappedBy = "role",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<UserEntity> users;
}
