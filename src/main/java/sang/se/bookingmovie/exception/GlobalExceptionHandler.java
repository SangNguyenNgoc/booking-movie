package sang.se.bookingmovie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sang.se.bookingmovie.error.ErrorResponse;

import java.sql.Timestamp;

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

    @ExceptionHandler(AllException.class)
    public ResponseEntity<?> handleAllException(AllException e) {
        return ResponseEntity.status(e.getStatus()).body(
                ErrorResponse.builder()
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .httpStatus(HttpStatus.valueOf(e.getStatus()))
                        .statusCode(e.getStatus())
                        .error(e.getError())
                        .messages(e.getMessages())
                        .build()
        );
    }
}
