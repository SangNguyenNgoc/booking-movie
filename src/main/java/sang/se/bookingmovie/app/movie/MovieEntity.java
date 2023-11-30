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

    @Column(name = "sub_name")
    private String subName;

    private String director;

    private String cast;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "running_time")
    private Integer runningTime;

    private String language;

    @Transient
    private Double rating;

    @Column(name = "number_of_ratings")
    private Integer numberOfRatings;

    @Column(name = "sum_of_ratings")
    private Integer sumOfRatings;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String poster;

    @Column(name = "horizontal_poster")
    private String horizontalPoster;

    private String trailer;

    private Integer rated;

    private String producer;

    private String slug;

    @OneToMany(
            mappedBy = "movie",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<ShowtimeEntity> showtimes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "movie_format",
            joinColumns = @JoinColumn(name = "movie_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "format_id", nullable = false)
    )
    private Set<FormatEntity> formats;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "genre_id", nullable = false)
    )
    private Set<MovieGenreEntity> genre;

    @OneToMany(
            mappedBy = "movie",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private Set<MovieImageEntity> images;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
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

    public Double getRating() {
        if (numberOfRatings > 0) {
            return Math.round((double) sumOfRatings / numberOfRatings * 10.0) / 10.0;
        } else {
            return 0.0;
        }

    }
}
