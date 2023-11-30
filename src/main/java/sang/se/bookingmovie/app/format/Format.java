package sang.se.bookingmovie.app.format;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Format {

    private Integer id;

    @NotBlank(message = "Caption MUST not be blank")
    private String caption;

    @NotBlank(message = "Version MUST not be blank")
    private String version;


}
