package sang.se.bookingmovie.app.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.role.RoleEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.verify = ?1, u.role = ?2 WHERE u.email = ?3")
    void updateVerifyAndRoleByEmail(Boolean verify, RoleEntity role, String email);

    Optional<UserEntity> findByEmail(String email);

    void deleteByEmail(String email);
}
