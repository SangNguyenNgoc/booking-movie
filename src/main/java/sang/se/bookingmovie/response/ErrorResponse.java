package sang.se.bookingmovie.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.List;

@Builder
public record ErrorResponse(
        Timestamp timestamp,
        HttpStatus httpStatus,
        Integer statusCode,
        String error,
        List<String>messages
) {
}
