package sang.se.bookingmovie.app.bill;

import java.io.UnsupportedEncodingException;

public interface IBillService {

    String create(String token, Bill bill);

    String pay(String transactionId);

    String refund();
    String create(String token, Bill bill) throws UnsupportedEncodingException;
}
