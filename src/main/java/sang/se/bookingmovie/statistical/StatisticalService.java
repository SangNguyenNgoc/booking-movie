package sang.se.bookingmovie.statistical;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.bill.BillRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticalService implements IStatisticalService {

    private final BillRepository billRepository;

    @Override
    public CardResponse getRevenue(LocalDate date) {
        Double revenue = 0.0;

        LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<ColumnResponse> allDatesInMonth = new ArrayList<>();

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            Double data = billRepository.findRevenueByDayAndMonth(
                    currentDate.getDayOfMonth(),
                    currentDate.getMonth().getValue(),
                    currentDate.getYear()
            ).orElse(0.0);
            allDatesInMonth.add(ColumnResponse.builder()
                    .content(data.toString())
                    .title(currentDate.toString())
                    .build());
            revenue += data;
            currentDate = currentDate.plusDays(1);
        }

        Double lastTimeRevenue = billRepository.findRevenueByMonth(date.getMonth().getValue() - 1, date.getYear())
                .orElse(null);
        double percent = 100.0;
        if(lastTimeRevenue != null) {
            percent = ((revenue - lastTimeRevenue) / lastTimeRevenue) * 100;
        }

        return CardResponse.builder()
                .title("TỔNG DOANH THU")
                .lastTime(Double.toString(percent))
                .content(Double.toString(revenue))
                .chart(allDatesInMonth)
                .build();
    }

    @Override
    public CardResponse getTotalTicket(LocalDate date) {
        Integer total = 0;

        LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<ColumnResponse> allDatesInMonth = new ArrayList<>();

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            Integer data = billRepository.findTotalTicketByDayAndMonth(
                    currentDate.getDayOfMonth(),
                    currentDate.getMonth().getValue(),
                    currentDate.getYear()
            ).orElse(0);
            allDatesInMonth.add(ColumnResponse.builder()
                    .content(data.toString())
                    .title(currentDate.toString())
                    .build());
            total += data;
            currentDate = currentDate.plusDays(1);
        }

        Integer lastTimeRevenue = billRepository.findTotalTicketByMonth(date.getMonth().getValue() - 1, date.getYear())
                .orElse(0);
        double percent = 100.0;
        if(lastTimeRevenue != 0) {
            percent = ((double) (total - lastTimeRevenue) / lastTimeRevenue) * 100;
        }

        return CardResponse.builder()
                .title("VÉ ĐÃ BÁN")
                .lastTime(Double.toString(percent))
                .content(Integer.toString(total))
                .chart(allDatesInMonth)
                .build();
    }
}
