package sang.se.bookingmovie.app.movie_genre;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieGenre {

    private Integer id;

    @NotBlank(message = "Genre name must not be blank")
    private String name;

}
