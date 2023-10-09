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
public abstract class AbstractException extends RuntimeException {
    private String error;
    private List<String> messages;
}
