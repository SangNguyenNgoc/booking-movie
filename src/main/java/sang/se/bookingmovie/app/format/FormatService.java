package sang.se.bookingmovie.app.format;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.response.ListResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormatService implements IFormatService {

    private final FormatMapper mapper;

    private final FormatRepository formatRepository;

    @Override
    public String create(Format format) {
        FormatEntity formatEntity = mapper.requestToEntity(format);
        formatRepository.save(formatEntity);
        return "Success";
    }

    @Override
    public ListResponse getAll() {
        List<FormatEntity> formatEntities = formatRepository.findAll();
        return ListResponse.builder()
                .total(formatEntities.size())
                .data(formatEntities.stream()
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
