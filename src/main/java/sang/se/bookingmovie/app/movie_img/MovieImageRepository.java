package sang.se.bookingmovie.app.movie_img;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieImageRepository extends JpaRepository<MovieImageEntity, Integer> {
    @Modifying
    @Query("DELETE from MovieImageEntity mi WHERE mi.id IN :ids")
    void deleteImages(@Param("ids") List<Integer> ids);
}
