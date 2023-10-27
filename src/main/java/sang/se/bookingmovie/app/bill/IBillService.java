package sang.se.bookingmovie.app.bill;

public interface IBillService {

    String create(String token, Bill bill);

    String pay(String transactionId);

    String refund();
}
