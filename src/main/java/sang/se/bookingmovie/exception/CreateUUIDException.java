package sang.se.bookingmovie.exception;

import java.util.List;

public class CreateUUIDException extends AbstractException {
    public CreateUUIDException(String error, List<String> messages) {
        super(error, messages);
    }
}
