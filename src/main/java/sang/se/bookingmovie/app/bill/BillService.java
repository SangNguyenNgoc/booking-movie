package sang.se.bookingmovie.app.bill;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillService {
    @Autowired
    private final BillRepository billRepository;
}
