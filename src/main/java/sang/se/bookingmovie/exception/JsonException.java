package sang.se.bookingmovie.exception;


import java.util.List;

public class JsonException extends AbstractException {
    public JsonException(String error, List<String> messages) {
        super(error, messages);
    }
}
