package sang.se.bookingmovie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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
          
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleException(DataNotFoundException e) {
        return ResponseEntity.status(404).body(
                ErrorResponse.builder()
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .statusCode(404)
                        .error(e.getError())
                        .messages(e.getMessages())
                        .build()
        );
    }

    @ExceptionHandler(JsonException.class)
    public ResponseEntity<?> handleException(JsonException e) {
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

    @ExceptionHandler(CreateUUIDException.class)
    public ResponseEntity<?> handleException(CreateUUIDException e) {
        return ResponseEntity.status(500).body(
                ErrorResponse.builder()
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .statusCode(500)
                        .error(e.getError())
                        .messages(e.getMessages())
                        .build()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleAppException(UserNotFoundException e) {
        return ResponseEntity.status(409).body(
                ErrorResponse.builder()
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .httpStatus(HttpStatus.CONFLICT)
                        .messages(List.of(e.getMessage()))
                        .build()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAppException(AuthenticationException e) {
        return ResponseEntity.status(401).body(
                ErrorResponse.builder()
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .messages(List.of(e.getMessage()))
                        .build()
        );
    }



}
