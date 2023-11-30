package sang.se.bookingmovie.app.seat_type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.response.ListResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatTypeService implements ISeatTypeService {
    private final SeatTypeRepository seatTypeRepository;
    private final SeatTypeMapper mapper;


    @Override
    public String create(SeatType seatType) {
        SeatTypeEntity seatTypeEntity = mapper.requestToEntity(seatType);
        seatTypeRepository.save(seatTypeEntity);
        return "success";
    }

    @Override
    public ListResponse getAll() {
        List<SeatTypeEntity> seatTypeEntities = seatTypeRepository.findAll();
        return ListResponse.builder()
                .total(seatTypeEntities.size())
                .data(seatTypeEntities.stream()
                        .map(mapper::entityToResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
