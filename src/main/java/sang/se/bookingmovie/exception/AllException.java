package sang.se.bookingmovie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllException extends RuntimeException {
    private String error;
    private Integer Status;
    private List<String> messages;
}
