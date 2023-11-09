package sang.se.bookingmovie.app.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @Query("SELECT COUNT(c) > 0 FROM CommentEntity c " +
            "WHERE c.user.id = :userId " +
            "AND c.movie.id = :movieId")
    boolean hasUserCommented(
            @Param("userId") String userId,
            @Param("movieId") String movieId
    );
}
