package sang.se.bookingmovie.statistical;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IStatisticalService {
    CardResponse getRevenue(LocalDate date);

    CardResponse getTotalTicket(LocalDate date);

    CardResponse getRevenueCinema(Integer month, Integer year);

    CardResponse getRevenueMovie(Integer month, Integer year);

    List<CardResponse> getStatistical(LocalDate date);

}
