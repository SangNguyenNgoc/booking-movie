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
        if (!checkShowtimeInData(showtimeRequests) || !checkShowtimeInList(showtimeRequests))
            throw new AllException("Data in valid", 400, List.of("The room already has showtime"));
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
            showtimeEntity.setStatus(true);
            showtimeRepository.save(showtimeEntity);
        }
        return "success";
    }

    @Override
    public ListResponse getShowtimeByCinemaAndDate(Date date, String cinemaId) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("Cinema is not exits")));
        List<MovieEntity> movieEntities = showtimeRepository.findByDateAndCinema(date, cinemaId);
        return ListResponse.builder()
                .total(movieEntities.size())
                .data(movieEntities.stream()
                        .peek(this::getFieldInShowtimeByCinemaAndDate)
                        .map(movieMapper::entityToResponse)
                        .peek(movieResponse -> {
                            movieResponse.setShowtimes(movieResponse.getShowtimes().stream()
                                    .sorted(Comparator.comparing(ShowtimeResponse::getStartTime))
                                    .collect(Collectors.toList())
                            );
                        })
                        .toList())
                .build();
    }

    @Override
    public List<CinemaResponse> getShowtimeByMovie(Date date, String movieId) {
        MovieEntity movieEntity = movieRepository.findById(movieId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("movie is not exist")));
        List<CinemaEntity> cinemaEntities = showtimeRepository.findByMovieAndDate(date, movieId);
        List<CinemaResponse> cinemaResponses = cinemaEntities.stream().map(cinemaEntity -> {
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
                .collect(Collectors.toList());
        return cinemaResponses;
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

    @Override
    public List<CinemaResponse> getAllCinemaDetailShowtime() {
        List<CinemaEntity> cinemaEntities = cinemaRepository.findAll();
        return cinemaEntities.stream().map(cinemaEntity -> {
                    List<MovieEntity> movieEntities = showtimeRepository.findByCinema(cinemaEntity.getId());
                    CinemaResponse cinemaResponse = cinemaMapper.entityToResponse(cinemaEntity);
                    cinemaResponse.setMovies(movieEntities.stream()
                            .peek(this::getFieldInShowtimeByCinemaAndDate)
                            .map(movieMapper::entityToResponse)
                            .collect(Collectors.toList()));
                    return cinemaResponse;
                })
                .collect(Collectors.toList());
    }

    private Boolean checkShowtimeInData(List<ShowtimeRequest> showtimeRequests){
        for (ShowtimeRequest newShowtime : showtimeRequests) {
            List<ShowtimeEntity> showtimeEntities = showtimeRepository.findByDateAndRoom(newShowtime.getStartDate(), newShowtime.getRoom());
            LocalTime isTime = newShowtime.getStartTime().toLocalTime();
            for (ShowtimeEntity showtimeEntity : showtimeEntities ) {
                LocalTime existTime = showtimeEntity.getStartTime().toLocalTime();
                if ( (isTime.isAfter(existTime)
                        && isTime.isBefore(existTime.plusMinutes(30 + showtimeEntity.getRunningTime())))
                        || (existTime.isAfter(isTime)
                        && existTime.isBefore(isTime.plusMinutes(30 + newShowtime.getRunningTime() )))
                ) {
                    return false;
                }
            }
        }
        return true;
    }

    private Boolean checkShowtimeInList(List<ShowtimeRequest> showtimeRequests){
        for (int i = 0; i < showtimeRequests.size() - 1; i++) {
            LocalTime isTime = showtimeRequests.get(i).getStartTime().toLocalTime();
            for (int j = i+1; j < showtimeRequests.size(); j++) {
                LocalTime exitsTime = showtimeRequests.get(j).getStartTime().toLocalTime();
                if ( ((isTime.isAfter(exitsTime) && isTime.isBefore(exitsTime.plusMinutes(30 + showtimeRequests.get(j).getRunningTime())))
                        || (exitsTime.isAfter(isTime)
                        && exitsTime.isBefore(isTime.plusMinutes(30 + showtimeRequests.get(i).getRunningTime()))))
                        && showtimeRequests.get(i).getRoom().equals(showtimeRequests.get(j).getRoom())
                ) {
                    return false;
                }
            }
        }
        return true;
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
