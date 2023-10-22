package sang.se.bookingmovie;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.room.RoomRepository;
import sang.se.bookingmovie.app.seat.SeatEntity;
import sang.se.bookingmovie.app.seat_room.SeatRoomEntity;
import sang.se.bookingmovie.app.seat_room.SeatRoomRepository;
import sang.se.bookingmovie.app.seat_type.SeatType;
import sang.se.bookingmovie.app.seat_type.SeatTypeEntity;
import sang.se.bookingmovie.app.seat_type.SeatTypeRepository;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class BookingMovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingMovieApplication.class, args);
	}

}
