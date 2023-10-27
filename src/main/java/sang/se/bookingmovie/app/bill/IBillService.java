package sang.se.bookingmovie.app.bill;

import java.io.UnsupportedEncodingException;

public interface IBillService {

    String create(String token, Bill bill) throws UnsupportedEncodingException;
}
