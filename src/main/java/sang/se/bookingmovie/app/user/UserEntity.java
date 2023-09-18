package sang.se.bookingmovie.app.user;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.bill.BillEntity;
import sang.se.bookingmovie.app.comment.CommentEntity;
import sang.se.bookingmovie.app.format.FormatEntity;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "role_id",
            nullable = false
    )
    private RoleEntity role;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<BillEntity> bills;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<CommentEntity> comments;
}
