package sang.se.bookingmovie.app.showtime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.app.format.FormatEntity;
import sang.se.bookingmovie.app.format.FormatRepository;
import sang.se.bookingmovie.app.movie.MovieEntity;
import sang.se.bookingmovie.app.movie.MovieRepository;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.room.RoomRepository;
import sang.se.bookingmovie.exception.AllException;
import sang.se.bookingmovie.utils.ApplicationUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeService implements IShowtimeService {

    private final ApplicationUtil applicationUtil;
    private final ShowtimeRepository showtimeRepository;
    private final ShowtimeMapper mapper;
    private final FormatRepository formatRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;

    private String createShowTimeID(){
        Long count = showtimeRepository.count() + 1;
        return "Showtime" + applicationUtil.addZeros(count,3);
    }

    @Override
    public String create(ShowtimeRequest showtimeRequest) {
        ShowtimeEntity showtimeEntity = mapper.requestToEntity(showtimeRequest);
        RoomEntity roomEntity = roomRepository.findById(showtimeRequest.getRoom())
                .orElseThrow(()->new AllException("Not Found", 404, List.of("Not found room id")));
        FormatEntity format = formatRepository.findById(showtimeRequest.getFormat())
                        .orElseThrow(() -> new AllException("Not found", 404, List.of("Not found format id")));
        MovieEntity movie = movieRepository.findById(showtimeRequest.getMovie())
                        .orElseThrow(() -> new AllException("Not found", 404, List.of("Not found movie id")));
        showtimeEntity.setId(createShowTimeID());
        showtimeEntity.setRoom(roomEntity);
        showtimeEntity.setFormat(format);
        showtimeEntity.setMovie(movie);
        showtimeRepository.save(showtimeEntity);
        return "success";
    }
}
