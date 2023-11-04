package sang.se.bookingmovie.statistical;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.bill.BillEntity;
import sang.se.bookingmovie.app.bill.BillRepository;
import sang.se.bookingmovie.app.cinema.CinemaEntity;
import sang.se.bookingmovie.app.cinema.CinemaRepository;
import sang.se.bookingmovie.app.movie.MovieEntity;
import sang.se.bookingmovie.app.movie.MovieRepository;
import sang.se.bookingmovie.exception.AllException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class  StatisticalService implements IStatisticalService {

    private final BillRepository billRepository;
    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;

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

    @Override
    public CardResponse getRevenueCinema(Integer month, Integer year) {
        if (month < 1 || month > 12) throw new AllException("Data invalid!", 400 , List.of("Month must be from 0 - 12"));
        Double revenue = 0.0;
        List<CinemaEntity> cinemaEntities = cinemaRepository.findAll();
        List<ColumnResponse> allCinemaInMonth = new ArrayList<>();
        String bestCinema = null;
        double bestRevenue = 0.0;
        for(CinemaEntity cinemaEntity : cinemaEntities){
            Double data = getTotalSumForBills(billRepository.findRevenueByMonthAndCinema(
                    month,
                    year,
                    cinemaEntity.getId()
            ));
            allCinemaInMonth.add(ColumnResponse.builder()
                    .content(data.toString())
                    .title(cinemaEntity.getName())
                    .build());
            if(data > bestRevenue){
                bestRevenue = data;
                bestCinema = cinemaEntity.getName();
            }
            revenue += data;
        }
            double percent = (bestRevenue /revenue) * 100;
        return CardResponse.builder()
                .title("RẠP CAO NHẤT")
                .lastTime(Double.toString(percent))
                .content(bestCinema)
                .chart(allCinemaInMonth)
                .build();
    }

    @Override
    public CardResponse getRevenueMovie(Integer month, Integer year) {
        if (month < 1 || month > 12) throw new AllException("Data invalid!", 400 , List.of("Month must be from 0 - 12"));
        Double revenue = 0.0;
        List< MovieEntity> movieEntities = movieRepository.findByMonthYear(month, year);
        List<ColumnResponse> allMovieInMonth = new ArrayList<>();
        String bestMovie = null;
        double bestRevenue = 0.0;
        for(MovieEntity movieEntity : movieEntities){
            Double data = getTotalSumForBills(billRepository.findRevenueByMonthAndMovie(
                    month,
                    year,
                    movieEntity.getId()
            ));
            allMovieInMonth.add(ColumnResponse.builder()
                    .content(data.toString())
                    .title(movieEntity.getName())
                    .build());
            if(data > bestRevenue){
                bestRevenue = data;
                bestMovie = movieEntity.getName();
            }
            revenue += data;
        }
        double percent = (bestRevenue /revenue) * 100;
        return CardResponse.builder()
                .title("PHIM CAO NHẤT")
                .lastTime(Double.toString(percent))
                .content(bestMovie)
                .chart(allMovieInMonth)
                .build();
    }

    public Double getTotalSumForBills(List<BillEntity> billEntities) {
        return billEntities.stream()
                .mapToDouble(BillEntity::getTotal)
                .sum();
    }

}
