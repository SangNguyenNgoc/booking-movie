package sang.se.bookingmovie.app.room_status;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.room.RoomEntity;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "room_status")
public class RoomStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_status_id")
    private Integer id;

    private String name;

    @OneToMany(
            mappedBy = "status",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private Set<RoomEntity> rooms;
}
