package sang.se.bookingmovie.app.seat_room;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SeatRoomRequest {
    @NotBlank(message = "seat room row must not be blank")
    @NotNull(message = "seat room row must not be null")
    private String row;
    @JsonProperty("row_index")
    @NotBlank(message = "seat room row index must not be blank")
    @NotNull(message = "seat room row index must not be null")
    private String rowIndex;
    @JsonProperty("type_id")
    @NotBlank(message = "seat room type must not be blank")
    @NotNull(message = "seat room type must not be null")
    private String typeId;
}
