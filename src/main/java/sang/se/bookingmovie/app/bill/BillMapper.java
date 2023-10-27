package sang.se.bookingmovie.app.bill;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.utils.IMapper;
import sang.se.bookingmovie.validate.ObjectsValidator;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BillMapper implements IMapper<BillEntity, Bill, BillResponse> {

    private final ModelMapper mapper;

    private final ObjectsValidator<Bill> validator;

    @Override
    public BillEntity requestToEntity(Bill bill) {
        validator.validate(bill);
        return null;
    }

    @Override
    public BillResponse entityToResponse(BillEntity billEntity) {
        BillResponse billResponse = mapper.map(billEntity, BillResponse.class);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        billResponse.setCreateTime(formatter.format(billEntity.getCreateTime()));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if(billResponse.getPaymentAt() != null) {
            billResponse.setPaymentAt(billEntity.getPaymentAt().format(dateTimeFormatter));
        }
        if(billResponse.getCancelDate() != null) {
            billResponse.setCancelDate(billEntity.getCancelDate().format(dateTimeFormatter));
        }
        return billResponse;
    }
}
