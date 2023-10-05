package sang.se.bookingmovie.app.cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.internal.bytebuddy.asm.Advice;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cinema {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotBlank(message = "Cinema name must not be blank")
    @NotNull(message = "Cinema name must not be null")
    private String name;

    @NotBlank(message = "Cinema address must not be blank")
    @NotNull(message = "Cinema address must not be null")
    private String address;

    @NotBlank(message = "Cinema district must not be blank")
    @NotNull(message = "Cinema district must not be null")
    private String district;

    @NotBlank(message = "Cinema city must not be blank")
    @NotNull(message = "Cinema city must not be null")
    private String city;
}
