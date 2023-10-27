package sang.se.bookingmovie.app.bill;

import jakarta.servlet.ServletException;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface IBillService {

    String create(String token, Bill bill) throws UnsupportedEncodingException;

    String pay(String transactionId) throws ServletException, JSONException, IOException;

    String refund();

}
