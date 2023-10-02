package sang.se.bookingmovie.exception;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataNotFoundException extends RuntimeException{
    private String error;
    private String message;
}
