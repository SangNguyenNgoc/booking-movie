package sang.se.bookingmovie.app.movie_img;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieImage {

    private Integer id;
    private String path;
    private String extension;
}
