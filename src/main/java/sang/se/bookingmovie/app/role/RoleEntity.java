package sang.se.bookingmovie.app.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
}
