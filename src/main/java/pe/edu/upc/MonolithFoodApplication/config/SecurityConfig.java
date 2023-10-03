package pe.edu.upc.MonolithFoodApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // ? CSRF: Es el que se encarga de manejar los tokens CSRF (Spring Security) ? //
            .csrf(csrf -> csrf.disable())
            // ? CORS: Permite que la aplicación acepte solicitudes CORS de cualquier origen (*) para métodos HTTP comunes (GET, HEAD, POST) ? //
            .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
            // ? Permite o bloquea la conexión a los endpoints ? //
            .authorizeHttpRequests(authRequest -> { authRequest
                .requestMatchers("/auth/**", "/auth/oauth2/**", "/auth/oauth2/callback/**").permitAll()
                // .requestMatchers("/v3/api-docs/**").permitAll() // Swagger API
                // .requestMatchers("/doc/swagger-ui/**").permitAll() // Swagger UI
                .anyRequest().authenticated();
            })
            // ? Oauth2 Login
            .oauth2Login(oauth2 -> oauth2
                .successHandler((request, response, authentication) -> 
                    // response.sendRedirect("/dashboard"))
                    response.sendRedirect("http://localhost:4200"))
                .failureHandler((request, response, exception) -> 
                    // response.sendRedirect("/login"))
                    response.sendRedirect("http://localhost:4200"))
            )
            // ? Session Management: Es el que se encarga de manejar las sesiones de los usuarios ? //
            .sessionManagement(sessionManager -> { sessionManager
                // ? STATELESS: No se manejarán las sesiones de los usuarios de Spring Security ? //
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            // ? Authentication Provider: Es el que se encarga de validar las credenciales de los usuarios ? //
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();

    }
}
