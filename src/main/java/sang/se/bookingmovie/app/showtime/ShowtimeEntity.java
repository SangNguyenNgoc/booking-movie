package sang.se.bookingmovie.app.showtime;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.format.FormatEntity;
import sang.se.bookingmovie.app.movie.MovieEntity;
import sang.se.bookingmovie.app.room.RoomEntity;
import sang.se.bookingmovie.app.ticket.TicketEntity;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "showtimes")
public class ShowtimeEntity {

    @Id
    @Column(name = "showtime_id")
    private String id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "running_time")
    private Integer runningTime;

    @Column(name = "status", columnDefinition = "boolean default true")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "movie_id",
            referencedColumnName = "movie_id",
            nullable = false
    )
    private MovieEntity movie;

    @OneToMany(
            mappedBy = "showtime",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<TicketEntity> tickets;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "format_id",
            referencedColumnName = "format_id",
            nullable = false
    )
    private FormatEntity format;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "room_id",
            referencedColumnName = "room_id",
            nullable = false
    )
    private RoomEntity room;

}
