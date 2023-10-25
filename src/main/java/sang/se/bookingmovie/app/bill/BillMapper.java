package sang.se.bookingmovie.app.bill;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;

@Service
@RequiredArgsConstructor
public class BillMapper implements IMapper<BillEntity, Bill, BillResponse> {
    @Override
    public BillEntity requestToEntity(Bill bill) {
        return null;
    }

    @Override
    public BillResponse entityToResponse(BillEntity billEntity) {
        return null;
    }
}
