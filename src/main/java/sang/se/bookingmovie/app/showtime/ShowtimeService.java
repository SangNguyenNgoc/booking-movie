package sang.se.bookingmovie.app.showtime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.cinema.CinemaEntity;
import sang.se.bookingmovie.app.cinema.CinemaMapper;
import sang.se.bookingmovie.app.cinema.CinemaRepository;
import sang.se.bookingmovie.app.cinema.CinemaResponse;
import sang.se.bookingmovie.app.format.FormatEntity;
import sang.se.bookingmovie.app.format.FormatRepository;
import sang.se.bookingmovie.app.movie.MovieEntity;
import sang.se.bookingmovie.app.movie.MovieMapper;
import sang.se.bookingmovie.app.movie.MovieRepository;
import sang.se.bookingmovie.app.movie.MovieResponse;
import sang.se.bookingmovie.app.movie_img.MovieImage;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.room.RoomRepository;
import sang.se.bookingmovie.app.seat_room.SeatRoomResponse;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
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
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;

    private String createShowTimeID() {
        long count = showtimeRepository.count() + 1;
        return "Showtime" + applicationUtil.addZeros(count, 3);
    }

    @Override
    public String create(List<ShowtimeRequest> showtimeRequests) {
        for (ShowtimeRequest showtimeRequest : showtimeRequests) {
            RoomEntity roomEntity = roomRepository.findById(showtimeRequest.getRoom())
                    .orElseThrow(() -> new AllException("Not Found", 404, List.of("Not found room id")));
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
    public CinemaResponse getShowtimeByCinemaAndDate(Date date, String cinemaId) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Cinema is not exits")));
        List<MovieEntity> movieEntities = showtimeRepository.findByDateAndCinema(date, cinemaId);
        CinemaResponse cinemaResponse = cinemaMapper.entityToResponse(cinemaEntity);
        cinemaResponse.setMovies(movieEntities.stream()
                .peek(this::getFieldInShowtimeByCinemaAndDate)
                .map(movieMapper::entityToResponse)
                .peek(movieResponse -> {
                    movieResponse.setShowtimes(movieResponse.getShowtimes().stream()
                            .sorted(Comparator.comparing(ShowtimeResponse::getStartTime))
                            .collect(Collectors.toList())
                    );
                })
                .toList()
        );
        return cinemaResponse;
    }

    @Override
    public MovieResponse getShowtimeByMovie(Date date, String movieId) {
        MovieEntity movieEntity = movieRepository.findById(movieId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("movie is not exist")));
        getFieldToDetail(movieEntity);
        MovieResponse movieResponse = movieMapper.entityToResponse(movieEntity);
        movieResponse.setImages(movieResponse.getImages().stream()
                .sorted(Comparator.comparing(MovieImage::getId))
                .collect(Collectors.toList())
        );
        List<CinemaEntity> cinemaEntities = showtimeRepository.findByMovieAndDate(date, movieId);
        movieResponse.setCinemas(cinemaEntities.stream().map(cinemaEntity -> {
                    Set<ShowtimeEntity> showtimeEntities = cinemaEntity.getRooms().stream()
                            .flatMap(roomEntity -> roomEntity.getShowtimes().stream())
                            .collect(Collectors.toSet());
                    CinemaResponse cinemaResponse = cinemaMapper.entityToResponse(cinemaEntity);
                    cinemaResponse.setShowtime(showtimeEntities.stream()
                            .peek(this::getFieldToList)
                            .map(showtimeMapper::entityToResponse)
                            .sorted(Comparator.comparing(ShowtimeResponse::getStartTime))
                            .collect(Collectors.toList()));
                    return cinemaResponse;
                })
                .collect(Collectors.toList()));
        return movieResponse;
    }

    @Override
    @Transactional
    public void updateStatusOfShowtime(LocalDate currentDate, LocalTime currentTime) {
        List<ShowtimeEntity> showtimeEntities = showtimeRepository.findAll();
        showtimeEntities.forEach(showtimeEntity -> {
            LocalDate startDate = showtimeEntity.getStartDate().toLocalDate();
            if (startDate.equals(currentDate)) {
                LocalTime startTime = showtimeEntity.getStartTime().toLocalTime();
                if (startTime.equals(currentTime) || currentTime.isAfter(startTime)) {
                    showtimeEntity.setStatus(false);
                }
            }
        });
    }

    @Override
    public ShowtimeResponse getSeatInShowTime(String showtimeId) {
        ShowtimeEntity showtimeEntity = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Showtime is not exits")));
        getFieldToGetSeat(showtimeEntity);
        showtimeEntity.getRoom().getSeats().forEach(seatRoomEntity -> {
            seatRoomEntity.setIsReserved(seatRoomEntity.getTickets().stream()
                    .anyMatch(ticketEntity -> ticketEntity.getShowtime().getId().equals(showtimeId)));
            seatRoomEntity.setTickets(null);
        });
        ShowtimeResponse showtimeResponse = showtimeMapper.entityToResponse(showtimeEntity);
        showtimeResponse.getRoom().setSeats(showtimeResponse.getRoom().getSeats().stream()
                .sorted(Comparator.comparing(SeatRoomResponse::getId))
                .toList());
        return showtimeResponse;
    }

    private void getFieldToGetSeat(ShowtimeEntity showtimeEntity) {
        getFieldToDetail(showtimeEntity.getMovie());
        showtimeEntity.setTickets(null);
    }

    private void getFieldToDetail(MovieEntity movieEntity) {
        movieEntity.setShowtimes(null);
        movieEntity.getStatus().setMovies(null);
    }

    private void getFieldToList(ShowtimeEntity showtimeEntity) {
        showtimeEntity.setRoom(null);
        showtimeEntity.setMovie(null);
        showtimeEntity.setFormat(null);
    }

    private void getFieldInShowtimeByCinemaAndDate(MovieEntity movieEntity) {
        movieEntity.setGenre(null);
        movieEntity.setStatus(null);
        movieEntity.setFormats(null);
        movieEntity.setComments(null);
        movieEntity.getShowtimes().forEach(this::getFieldToList);
    }


}
