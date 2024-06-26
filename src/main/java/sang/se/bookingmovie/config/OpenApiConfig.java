package sang.se.bookingmovie.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Sang Nguyễn",
                        email = "nngocsang38@gmail.com",
                        url = "https://github.com/SangNguyenNgoc/course-registration.git"
                ),
                description = "Open Api for booking movie app",
                title = "OpenApi booking movie - Sang Nguyễn",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Heroku Application",
                        url = "https://booking-movie-backend-3a547b1ac2e9.herokuapp.com"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "JWT to authentication and authorization",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
