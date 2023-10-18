package sang.se.bookingmovie.app.showtime;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.cinema.CinemaEntity;
import sang.se.bookingmovie.app.cinema.CinemaMapper2;
import sang.se.bookingmovie.app.cinema.CinemaResponse;
import sang.se.bookingmovie.app.format.FormatEntity;
import sang.se.bookingmovie.app.format.FormatRepository;
import sang.se.bookingmovie.app.movie.MovieEntity;
import sang.se.bookingmovie.app.movie.MovieMapper;
import sang.se.bookingmovie.app.movie.MovieRepository;
import sang.se.bookingmovie.app.movie.MovieResponse;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.room.RoomRepository;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowtimeService implements IShowtimeService {

    private final ApplicationUtil applicationUtil;
    private final ShowtimeRepository showtimeRepository;
    private final ShowtimeMapper showtimeMapper;
    private final MovieMapper movieMapper;
    private final FormatRepository formatRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final CinemaMapper2 cinemaMapper2;

    private String createShowTimeID(){
        long count = showtimeRepository.count() + 1;
        return "Showtime" + applicationUtil.addZeros(count,3);
    }

    @Override
    public String create(List<ShowtimeRequest> showtimeRequests) {
        for (ShowtimeRequest showtimeRequest: showtimeRequests) {
            RoomEntity roomEntity = roomRepository.findById(showtimeRequest.getRoom())
                    .orElseThrow(()->new AllException("Not Found", 404, List.of("Not found room id")));
            FormatEntity format = formatRepository.findById(showtimeRequest.getFormat())
                    .orElseThrow(() -> new AllException("Not found", 404, List.of("Not found format id")));
            MovieEntity movie = movieRepository.findById(showtimeRequest.getMovie())
                    .orElseThrow(() -> new AllException("Not found", 404, List.of("Not found movie id")));
            ShowtimeEntity showtimeEntity = showtimeMapper.requestToEntity(showtimeRequest);
            showtimeEntity.setId(createShowTimeID());
            showtimeEntity.setRoom(roomEntity);
            showtimeEntity.setFormat(format);
            showtimeEntity.setMovie(movie);
            showtimeRepository.save(showtimeEntity);
        }
        return "success";
    }

    @Override
    public ListResponse getShowtimeByCinemaAndDate(Date date, String cinemaId) {
        List<MovieEntity> showtimeEntities = showtimeRepository.findByDateAndCinema(date, cinemaId);
        List<MovieResponse> movieResponses = showtimeEntities.stream()
                .map(movieMapper::entityToResponse)
                .toList();
        movieResponses.forEach(movieResponse -> {
            movieResponse.setGenre(null);
            movieResponse.setStatus(null);
            movieResponse.setFormats(null);
            movieResponse.setComments(null);
        });
        return ListResponse.builder()
                .total(movieResponses.size())
                .data(Collections.singletonList(movieResponses))
                .build();
    }

    @Override
    public ListResponse getShowtimeByMovie(Date date, String movieId) {
        List<CinemaEntity> cinemaEntities = showtimeRepository.findByMovieAndDate(date, movieId);
        List<CinemaResponse> cinemaResponses = new ArrayList<>();
        for (CinemaEntity cinemaEntity: cinemaEntities) {
            List<ShowtimeEntity> showtimeEntities = showtimeRepository.findByCinemaId(date, cinemaEntity.getId());
            CinemaResponse cinemaResponse = cinemaMapper2.entityToResponse(cinemaEntity);
            cinemaResponse.setShowtime(showtimeEntities.stream()
                    .map(showtimeMapper::entityToResponse)
                    .collect(Collectors.toList()));
            cinemaResponses.add(cinemaResponse);
        }
        return ListResponse.builder()
                .total(cinemaResponses.size())
                .data(new ArrayList<>(cinemaResponses))
                .build();
    }

    @Override
    @Transactional
    public void updateStatusOfShowtime(LocalDate currentDate, LocalTime currentTime) {
        List<ShowtimeEntity> showtimeEntities = showtimeRepository.findAll();
        showtimeEntities.forEach(showtimeEntity -> {
            LocalDate startDate = showtimeEntity.getStartDate().toLocalDate();
            if (startDate.equals(currentDate)){
                LocalTime startTime = showtimeEntity.getStartTime().toLocalTime();
                if (startTime.equals(currentTime) || currentTime.isAfter(startTime)) {
                    showtimeEntity.setStatus(false);
                }
            }
        });
    }


}
