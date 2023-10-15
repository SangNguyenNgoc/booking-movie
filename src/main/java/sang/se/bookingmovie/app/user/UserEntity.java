package sang.se.bookingmovie.app.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sang.se.bookingmovie.app.bill.BillEntity;
import sang.se.bookingmovie.app.comment.CommentEntity;
import sang.se.bookingmovie.app.format.FormatEntity;
import sang.se.bookingmovie.app.role.RoleEntity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "user_id")
    private String id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    private Boolean gender;

    private Integer point = 0;

    private Boolean verify;

    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return id;
    }

    public String getName() { return username; }

    public Boolean getVerify() {
        return verify;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
