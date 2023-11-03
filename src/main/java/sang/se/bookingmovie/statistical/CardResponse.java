package sang.se.bookingmovie.statistical;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardResponse {

    private String title;
    private String content;
    @JsonProperty("last_time")
    private String lastTime;
    List<ColumnResponse> chart;

}
