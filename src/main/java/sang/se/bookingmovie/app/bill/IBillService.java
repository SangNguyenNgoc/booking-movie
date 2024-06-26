package sang.se.bookingmovie.app.bill;

import jakarta.servlet.ServletException;
import org.json.JSONException;
import sang.se.bookingmovie.response.ListResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;

public interface IBillService {

    String create(String token, Bill bill) throws UnsupportedEncodingException;

    String pay(String token, String transactionId) throws ServletException, JSONException, IOException;

    String refund(String billId, String reason);

    ListResponse getBillInUser(String token, String userId, Integer page, Integer size, Date date);

    BillResponse getDetail(String token, String billId);

    BillResponse getDetail(String billId);

}
