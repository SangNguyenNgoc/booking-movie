package sang.se.bookingmovie.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.movie.MovieService;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.room.RoomRepository;
import sang.se.bookingmovie.app.seat_room.SeatRoomEntity;
import sang.se.bookingmovie.app.seat_room.SeatRoomRepository;
import sang.se.bookingmovie.app.seat_type.SeatTypeEntity;
import sang.se.bookingmovie.app.seat_type.SeatTypeRepository;
import sang.se.bookingmovie.app.showtime.ShowtimeService;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTasks {

    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    @PostConstruct
    @Scheduled(cron = "0 0 2 * * ?")
    public void setMovieStatus() {
        LocalDate currentDate = LocalDate.now();
        movieService.updateStatusOfMovie(currentDate);
    }

    @PostConstruct
    @Scheduled(fixedRate = 60000) // Chạy mỗi 60 giây
    public void changeShowtimeStatus() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        showtimeService.updateStatusOfShowtime(currentDate, currentTime);
    }

//    @PostConstruct
//    @Transactional
//    public void dataLoader() {
//        List<RoomEntity> roomEntities = roomRepository.findAll();
//        SeatTypeEntity vip = seatTypeRepository.findById(2).orElse(null);
//        SeatTypeEntity normal = seatTypeRepository.findById(1).orElse(null);
//        int ascii = 65;
//        for (RoomEntity room : roomEntities) {
//            for (int i = 0; i < 10; i++) {
//                for (int j = 0; j < 15; j++) {
//                    SeatRoomEntity seatRoomEntity;
//                    if (i > 3 && i < 8 && j >= 5 && j <= 9) {
//                        seatRoomEntity = SeatRoomEntity.builder()
//                                .id(null)
//                                .row(String.valueOf((char) ascii))
//                                .rowIndex(j+1)
//                                .type(null)
//                                .room(null)
//                                .status(true)
//                                .build();
//                        seatRoomEntity.setRoom(room);
//                        seatRoomEntity.setType(vip);
//                    } else {
//                        seatRoomEntity = SeatRoomEntity.builder()
//                                .id(null)
//                                .row(String.valueOf((char) ascii))
//                                .rowIndex(j+1)
//                                .status(true)
//                                .build();
//                        seatRoomEntity.setRoom(room);
//                        seatRoomEntity.setType(normal);
//                    }
//                    seatRoomRepository.save(seatRoomEntity);
//                }
//                ascii++;
//            }
//            ascii = 65;
//        }
//    }

}
