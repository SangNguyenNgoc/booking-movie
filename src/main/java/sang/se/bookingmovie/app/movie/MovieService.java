package sang.se.bookingmovie.app.movie;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sang.se.bookingmovie.app.format.FormatRepository;
import sang.se.bookingmovie.app.movie_genre.MovieGenreRepository;
import sang.se.bookingmovie.app.movie_img.MovieImage;
import sang.se.bookingmovie.app.movie_img.MovieImageEntity;
import sang.se.bookingmovie.app.movie_img.MovieImageRepository;
import sang.se.bookingmovie.app.movie_status.MovieStatus;
import sang.se.bookingmovie.app.movie_status.MovieStatusEntity;
import sang.se.bookingmovie.app.movie_status.MovieStatusMapper;
import sang.se.bookingmovie.app.movie_status.MovieStatusRepository;
import sang.se.bookingmovie.exception.DataNotFoundException;
import sang.se.bookingmovie.response.ListResponse;
import sang.se.bookingmovie.utils.ApplicationUtil;
import sang.se.bookingmovie.utils.DiscordService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;

    private final MovieStatusRepository movieStatusRepository;

    private final MovieGenreRepository movieGenreRepository;

    private final FormatRepository formatRepository;

    private final MovieImageRepository movieImageRepository;

    private final MovieMapper movieMapper;

    private final MovieStatusMapper statusMapper;

    private final DiscordService discordService;

    private final ApplicationUtil applicationUtil;

    @Override
    public ListResponse getMoviesByStatus(String slug, Integer page, Integer size) {
        List<MovieStatus> movieStatusList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("releaseDate"));
        if (slug != null) {
            MovieStatusEntity movieStatusEntity = movieStatusRepository.findStatusAndMovieBySlug(slug)
                    .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("status is not exist")));
            movieStatusEntity.setMovies(null);
            MovieStatus movieStatus = statusMapper.entityToResponse(movieStatusEntity);
            movieStatus.setMovies(movieRepository.findByStatus(slug, pageable)
                    .stream()
                    .peek(this::getFieldToList)
                    .map(movieMapper::entityToResponse)
                    .toList());
            movieStatusList.add(movieStatus);
        } else {
            List<MovieStatusEntity> movieStatusEntities = movieStatusRepository.findAll();
            movieStatusList = movieStatusEntities.stream()
                    .map(movieStatus -> {
                        movieStatus.setMovies(null);
                        return statusMapper.entityToResponse(movieStatus);
                    })
                    .peek(movieStatus -> {
                        String slugTemp = movieStatus.getSlug();
                        movieStatus.setMovies(movieRepository.findByStatus(slugTemp, pageable)
                                .stream()
                                .peek(this::getFieldToList)
                                .map(movieMapper::entityToResponse)
                                .toList());
                    })
                    .toList();
        }
        return ListResponse.builder()
                .total(movieStatusList.size())
                .data(movieStatusList)
                .build();
    }

    @Override
    public String create(String movieJson, MultipartFile poster, MultipartFile horPoster, List<MultipartFile> images) {
        MovieEntity movieEntity = movieMapper.jsonToEntity(movieJson);
        movieEntity.setId(createId());
        movieEntity.setSlug(applicationUtil.toSlug(movieEntity.getName() + " " + movieEntity.getSubName()));
        movieEntity.setPoster(discordService.sendImage(poster, true));
        movieEntity.setHorizontalPoster(discordService.sendImage(horPoster, true));
        MovieStatusEntity movieStatusEntity = movieStatusRepository.findBySlug("coming-soon")
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("status is not exist")));
        movieEntity.setStatus(movieStatusEntity);
        movieGenreRepository.findById(movieEntity.getGenre().getId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Data not found",
                        List.of("genre with id: " + movieEntity.getGenre().getId() + " is not exist")
                ));
        movieEntity.getFormats().forEach(formatEntity -> {
            formatRepository.findById(formatEntity.getId())
                    .orElseThrow(() -> new DataNotFoundException(
                            "Data not found",
                            List.of("format with id: " + formatEntity.getId() + " is not exist")
                    ));
            formatEntity.setMovies(Set.of(movieEntity));
        });
        movieEntity.setImages(createMovieImage(movieEntity, images));
        movieRepository.save(movieEntity);
        return "Success";
    }

    @Override
    public ListResponse getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by("releaseDate"));
        List<MovieResponse> movieResponses = movieRepository.findAll(pageRequest).stream()
                .peek(this::getFieldToList)
                .map(movieMapper::entityToResponse)
                .toList();
        return ListResponse.builder()
                .total(movieResponses.size())
                .data(movieResponses)
                .build();
    }

    @Override
    public MovieResponse getMovieById(String movieId) {
        MovieEntity movieEntity = movieRepository.findById(movieId)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("movie is not exist")));
        getFieldToDetail(movieEntity);
        MovieResponse movieResponse = movieMapper.entityToResponse(movieEntity);
        movieResponse.setImages(
                movieResponse.getImages().stream()
                        .sorted(Comparator.comparing(MovieImage::getId))
                        .collect(Collectors.toList())
        );
        return movieResponse;
    }

    @Override
    public MovieResponse getMovieBySlug(String slug) {
        MovieEntity movieEntity = movieRepository.findBySlug(slug)
                .orElseThrow(() -> new DataNotFoundException("Data not found", List.of("movie is not exist")));
        getFieldToDetail(movieEntity);
        MovieResponse movieResponse = movieMapper.entityToResponse(movieEntity);
        movieResponse.setImages(
                movieResponse.getImages().stream()
                        .sorted(Comparator.comparing(MovieImage::getId))
                        .collect(Collectors.toList())
        );
        return movieResponse;
    }

    @Override
    @Transactional
    public String updateStatusOfMovie(String movieId, Integer statusId) {
        MovieEntity movieEntity = findMovieById(movieId);
        MovieStatusEntity movieStatusEntity = movieStatusRepository.findById(statusId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Data not found",
                        List.of("status with id " + statusId + " is not exist")));
        movieEntity.setStatus(movieStatusEntity);
        return "Success";
    }

    @Override
    public void updateStatusOfMovie(LocalDate currentDate) {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        movieEntities.forEach(movieEntity -> {
            LocalDate releaseDateMovie = movieEntity.getReleaseDate().toLocalDate();
            if (currentDate.isAfter(releaseDateMovie) || currentDate.isEqual(releaseDateMovie)) {
                updateStatusOfMovie(movieEntity.getId(), 2);
            }
            LocalDate endDateMovie = movieEntity.getEndDate().toLocalDate();
            if (currentDate.isAfter(endDateMovie)) {
                updateStatusOfMovie(movieEntity.getId(), 3);
            }
        });
    }

    @Override
    public String updateMovie(String movieId, Movie movie) {
        MovieEntity movieEntityAfter = movieMapper.requestToEntity(movie);
        MovieEntity movieEntityBefore = findMovieById(movieId);
        movieEntityAfter.setId(movieId);
        movieEntityAfter.setStatus(movieEntityBefore.getStatus());
        movieEntityAfter.setSlug(applicationUtil.toSlug(movieEntityAfter.getName()));
        movieEntityAfter.setPoster(movieEntityBefore.getPoster());
        movieGenreRepository.findById(movieEntityAfter.getGenre().getId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Data not found",
                        List.of("genre with id: " + movie.getGenre().getId() + " is not exist")
                ));
        movieEntityAfter.getFormats().forEach(formatEntity -> {
            formatRepository.findById(formatEntity.getId())
                    .orElseThrow(() -> new DataNotFoundException(
                            "Data not found",
                            List.of("format with id: " + formatEntity.getId() + " is not exist")
                    ));
            formatEntity.setMovies(Set.of(movieEntityAfter));
        });
        movieRepository.save(movieEntityAfter);
        return "Success";
    }

    @Override
    @Transactional
    public String updatePoster(String movieId, MultipartFile poster) {
        MovieEntity movieEntity = findMovieById(movieId);
        movieEntity.setPoster(discordService.sendImage(poster, true));
        return "Success";
    }

    @Override
    @Transactional
    public String updateImages(String movieId, List<MultipartFile> images, List<Integer> imageIds) {
        if (imageIds != null && !imageIds.isEmpty()) {
            movieImageRepository.deleteAllById(imageIds);
        }
        if (images != null && !images.isEmpty()) {
            MovieEntity movieEntity = findMovieById(movieId);
            Set<MovieImageEntity> movieImageEntities = movieEntity.getImages();
            movieImageEntities.addAll(createMovieImage(movieEntity, images));
            movieEntity.setImages(movieImageEntities);
        }
        return "Success";
    }

    @Override
    public ListResponse searchBySlug(String input, Integer page, Integer size) {
        input = applicationUtil.toSlug(input);
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("releaseDate"));
        List<MovieEntity> movieEntities = movieRepository.searchBySlug(input, pageable);
        return ListResponse.builder()
                .total(movieEntities.size())
                .data(movieEntities.stream()
                        .map(movieEntity -> {
                            getFieldToList(movieEntity);
                            return movieMapper.entityToResponse(movieEntity);
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

    private void getFieldToList(MovieEntity movieEntity) {
        movieEntity.getStatus().setMovies(null);
        movieEntity.setShowtimes(null);
        movieEntity.setComments(null);
        movieEntity.setImages(null);
        movieEntity.setFormats(null);
        movieEntity.setGenre(null);
    }

    private void getFieldToDetail(MovieEntity movieEntity) {
        movieEntity.setShowtimes(null);
        movieEntity.getStatus().setMovies(null);
    }

    private Set<MovieImageEntity> createMovieImage(MovieEntity movieEntity, List<MultipartFile> images) {
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
        return "mv-" + applicationUtil.addZeros(movieRepository.count() + 1, 4);
    }

    private MovieEntity findMovieById(String movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Data not found",
                        List.of("movie with id " + movieId + " is not exist")
                ));
    }


}

