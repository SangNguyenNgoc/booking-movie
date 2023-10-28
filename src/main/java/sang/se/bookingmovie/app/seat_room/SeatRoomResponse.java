package sang.se.bookingmovie.app.seat_room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sang.se.bookingmovie.app.seat_type.SeatType;
import sang.se.bookingmovie.app.ticket.TicketResponse;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeatRoomResponse {
    @JsonProperty("seat_id") private Integer id;
    private Boolean status;
    private String row;
    @JsonProperty("is_reserved")
    @JsonInclude(JsonInclude.Include.NON_NULL) private Boolean isReserved;
    @JsonProperty("row_index") private Integer rowIndex;
    @JsonInclude(JsonInclude.Include.NON_NULL) private SeatType type;
}
