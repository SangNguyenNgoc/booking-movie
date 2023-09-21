package sang.se.bookingmovie.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ListResponse(Integer total, List<Object> data) {
}
