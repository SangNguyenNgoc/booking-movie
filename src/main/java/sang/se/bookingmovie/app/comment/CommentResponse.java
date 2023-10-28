package sang.se.bookingmovie.app.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Integer id;

    @NotBlank(message = "Content MUST not be blank")
    private String content;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "Movie id MUST not be blank")
    private String movieId;

    @JsonProperty("create_date")
    private String createDate;

    private String user;
}
