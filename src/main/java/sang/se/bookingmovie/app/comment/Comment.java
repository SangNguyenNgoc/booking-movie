package sang.se.bookingmovie.app.comment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Rating MUST not be null")
    @Min(value = 1, message = "Rating MUST be at least 1")
    @Max(value = 10, message = "Rating MUST be less than or equal to 10")
    private Integer rating;
}
