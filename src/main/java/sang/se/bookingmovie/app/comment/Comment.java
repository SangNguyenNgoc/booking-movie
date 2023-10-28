package sang.se.bookingmovie.app.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @NotBlank(message = "Content MUST not be blank")
    private String content;

    @NotBlank(message = "Movie id MUST not be blank")
    private String movieId;
}
