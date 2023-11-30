package sang.se.bookingmovie.exception;

import java.util.List;

public class DataNotFoundException extends AbstractException {
    public DataNotFoundException(String error, List<String> messages) {
        super(error, messages);
    }
}
