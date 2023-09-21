package sang.se.bookingmovie.app.comment;

import jakarta.persistence.*;
import lombok.*;
import sang.se.bookingmovie.app.movie.MovieEntity;
import sang.se.bookingmovie.app.user.UserEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @Column(name = "comment_id")
    private String id;

    private String content;

    private String status;

    @Column(name = "comment_rating")
    private String commentRating;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "movie_id",
            referencedColumnName = "movie_id",
            nullable = false
    )
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id",
            nullable = false
    )
    private UserEntity user;
}
