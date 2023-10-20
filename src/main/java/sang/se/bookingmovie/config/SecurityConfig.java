package sang.se.bookingmovie.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sang.se.bookingmovie.auth.SuccessHandler;
import sang.se.bookingmovie.filter.AuthFilter;
import sang.se.bookingmovie.filter.MyCorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthFilter authFilter;

    private final MyCorsFilter myCorsFilter;

    private final AuthenticationProvider authenticationProvider;

    private final SuccessHandler successHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/v1/admin/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("api/v1/guest/**").hasAnyAuthority("ROLE_GUEST", "ROLE_ADMIN")
                        .anyRequest().permitAll()
                )
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                })
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(myCorsFilter, ChannelProcessingFilter.class)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorization")
                .and()
                .redirectionEndpoint().baseUri("/oauth2/callback/*")
                .and()
                .successHandler(successHandler)
                .and()
                .build();
    }

}
