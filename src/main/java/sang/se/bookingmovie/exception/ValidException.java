package sang.se.bookingmovie.exception;

import java.util.List;


public class ValidException extends AbstractException {
    public ValidException(String error, List<String> messages) {
        super(error, messages);
    }
}
