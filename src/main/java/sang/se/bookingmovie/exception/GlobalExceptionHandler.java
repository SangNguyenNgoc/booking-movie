package sang.se.bookingmovie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sang.se.bookingmovie.response.ErrorResponse;

import java.sql.Timestamp;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidException.class)
    public ResponseEntity<?> handleException(ValidException e) {
        return ResponseEntity.status(400).body(
                ErrorResponse.builder()
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(400)
                        .error(e.getError())
                        .messages(e.getMessages())
                        .build()
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleException(DataNotFoundException e) {
        return ResponseEntity.status(404).body(
                ErrorResponse.builder()
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .statusCode(404)
                        .error(e.getError())
                        .messages(List.of(e.getMessage()))
                        .build()
        );
    }
}
