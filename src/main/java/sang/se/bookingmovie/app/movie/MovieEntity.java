package sang.se.bookingmovie.app.movie;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.comment.CommentEntity;
import sang.se.bookingmovie.app.format.FormatEntity;
import sang.se.bookingmovie.app.movie_genre.MovieGenreEntity;
import sang.se.bookingmovie.app.movie_img.MovieImageEntity;
import sang.se.bookingmovie.app.movie_status.MovieStatusEntity;
import sang.se.bookingmovie.app.showtime.ShowtimeEntity;

import java.sql.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @Column(name = "movie_id")
    private String id;

    private String name;

    private String director;

    private String cast;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "running_time")
    private Integer runningTime;

    private String language;

    private Double rating;

    @Column(name = "number_of_ratings")
    private Integer numberOfRatings;

    private String description;

    private String trailer;

    private String rated;

    private String producer;

    private String slug;

    @OneToMany(
            mappedBy = "movie",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<ShowtimeEntity> showtimes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_format",
            joinColumns = @JoinColumn(name = "movie_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "format_id", nullable = false)
    )
    private Set<FormatEntity> format;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "genre_id",
            referencedColumnName = "movie_genre_id",
            nullable = false
    )
    private MovieGenreEntity genre;

    @OneToMany(
            mappedBy = "movie",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<MovieImageEntity> images;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "status_id",
            referencedColumnName = "movie_status_id",
            nullable = false
    )
    private MovieStatusEntity status;

    @OneToMany(
            mappedBy = "movie",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<CommentEntity> comments;
}
