package sang.se.bookingmovie.app.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.user.UserResponse;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    private Integer id;

    @NotBlank(message = "Content MUST not be blank")
    private String content;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String movieId;

    @JsonProperty("create_date")
    private String createDate;

    private String user;
}
