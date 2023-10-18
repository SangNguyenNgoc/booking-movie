package sang.se.bookingmovie.exception;

import java.util.List;

public class UserNotFoundException extends AbstractException {

    public UserNotFoundException(String error, List<String> messages) {
        super(error, messages);
    }
}
