package sang.se.bookingmovie.app.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sang.se.bookingmovie.app.movie_genre.MovieGenre;
import sang.se.bookingmovie.app.movie_genre.MovieGenreEntity;
import sang.se.bookingmovie.app.movie_genre.MovieGenreRepository;
import sang.se.bookingmovie.app.movie_img.MovieImage;
import sang.se.bookingmovie.app.movie_img.MovieImageEntity;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusEntity;
import sang.se.bookingmovie.app.movie_status.MovieStatusMapper;
import sang.se.bookingmovie.app.movie_status.MovieStatusRepository;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.DiscordService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;

    private final MovieStatusRepository movieStatusRepository;

    private final MovieGenreRepository movieGenreRepository;

    private final MovieMapper movieMapper;

    private final MovieStatusMapper statusMapper;

    private final DiscordService discordService;

    private final ApplicationUtil applicationUtil;

    @Override
    public List<MovieStatus> getMoviesByStatus(String slug) {
        List<MovieStatusEntity> movieStatusEntities = new ArrayList<>();
        if(slug != null) {
            MovieStatusEntity movieStatusEntity = movieStatusRepository.findStatusAndMovieBySlug(slug)
                    .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("status is not exist")));
            movieStatusEntities.add(movieStatusEntity);
        } else {
            movieStatusEntities = movieStatusRepository.findAll();
        }
        return movieStatusEntities.stream()
                .peek(movieStatus -> movieStatus.getMovies().forEach(this::getFieldToLanding))
                .map(statusMapper::entityToResponse)
                .peek(movieStatus -> movieStatus.setMovies(sortByReleaseDate(movieStatus.getMovies())))
                .collect(Collectors.toList());
    }

    @Override
    public String create(String movieJson, MultipartFile poster, List<MultipartFile> images) {
        MovieEntity movieEntity = movieMapper.jsonToEntity(movieJson);
        movieEntity.setId(createId());
        movieEntity.setSlug(applicationUtil.toSlug(movieEntity.getName()));
        movieEntity.setPoster(discordService.sendImage(poster, true));
        MovieStatusEntity movieStatusEntity = movieStatusRepository.findBySlug("coming-soon")
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("status is not exist")));
        movieEntity.setStatus(movieStatusEntity);
        movieEntity.setImages(createMovieImage(movieEntity, images));
        movieEntity.getFormats()
                .forEach(formatEntity -> formatEntity.setMovies(Set.of(movieEntity)));
        movieRepository.save(movieEntity);
        return "success";
    }

    @Override
    public ListResponse getAll() {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        return ListResponse.builder()
                .total(movieEntities.size())
                .data(movieEntities.stream()
                        .peek(this::getFieldToAdmin)
                        .map(movieMapper::entityToResponse)
                        .sorted(Comparator.comparing(MovieResponse::getReleaseDate))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public MovieResponse getMovieBySlug(String slug) {
        MovieEntity movieEntity = movieRepository.findBySlug(slug)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("movie is not exist")));
        getFieldToAdmin(movieEntity);
        MovieResponse movieResponse = movieMapper.entityToResponse(movieEntity);
        movieResponse.setImages(
                movieResponse.getImages().stream()
                        .sorted(Comparator.comparing(MovieImage::getId))
                        .collect(Collectors.toList())
        );
        return  movieResponse;

    }

    private List<MovieResponse> sortByReleaseDate(List<MovieResponse> movies) {
        return movies.stream()
                .sorted(Comparator.comparing(MovieResponse::getReleaseDate))
                .toList();
    }

    private void getFieldToLanding(MovieEntity movieEntity) {
        movieEntity.setStatus(null);
        movieEntity.setShowtimes(null);
        movieEntity.setComments(null);
        movieEntity.setImages(null);
        movieEntity.setFormats(null);
        movieEntity.setGenre(null);
    }

    private void getFieldToAdmin(MovieEntity movieEntity) {
        movieEntity.setShowtimes(null);
        movieEntity.getStatus().setMovies(null);
    }

    private  Set<MovieImageEntity> createMovieImage(MovieEntity movieEntity, List<MultipartFile> images) {
        Set<MovieImageEntity> movieImageEntities = new HashSet<>();
        images.forEach(multipartFile -> {
            String cdnUrl = discordService.sendImage(multipartFile, false);
            String extension = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(
                    multipartFile.getOriginalFilename().lastIndexOf(".") + 1
            );
            movieImageEntities.add(MovieImageEntity.builder()
                            .path(cdnUrl)
                            .extension(extension)
                            .movie(movieEntity)
                            .build());
        });
        return movieImageEntities;
    }

    private String createId() {
        return "movie-" + applicationUtil.addZeros(movieRepository.count() + 1, 4);
    }

    public String test() {
        discordService.sendMessage("I'm ready");
        return "Success";
    }

}

