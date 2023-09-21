package sang.se.bookingmovie.exception;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidException extends RuntimeException{
    private String error;
    private List<String> messages;
}
