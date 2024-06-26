package sang.se.bookingmovie.app.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String content;

    private Integer rating;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String movie;

    @JsonProperty("create_date")
    private String createDate;

    private String user;

    @JsonProperty("user_id")
    private String userId;

    private CommentStatus status;

    @JsonProperty("avatar_user")
    private String avatarUser;
}
