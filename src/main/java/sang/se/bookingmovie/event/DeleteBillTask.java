package sang.se.bookingmovie.event;

import lombok.RequiredArgsConstructor;
import sang.se.bookingmovie.app.bill.BillEntity;
import sang.se.bookingmovie.app.bill.BillRepository;

import java.util.Optional;
import java.util.TimerTask;

@RequiredArgsConstructor
public class DeleteBillTask extends TimerTask {

    private final BillRepository billRepository;

    private final String billId;

    @Override
    public void run() {
        Optional<BillEntity> billEntity = billRepository.findById(billId);
        if (billEntity.isPresent() && billEntity.get().getStatus().getId() == 1) {
            billRepository.deleteById(billId);
        }
        cancel();
    }
}
