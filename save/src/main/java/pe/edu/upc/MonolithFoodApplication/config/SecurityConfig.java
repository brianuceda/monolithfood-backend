package pe.edu.upc.MonolithFoodApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import pe.edu.upc.MonolithFoodApplication.filters.JwtAuthenticationFilter;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationProvider authProvider;
    private static final String[] ALLOWED_ORIGINS = { "https://monolithfood.vercel.app", "http://localhost:4200" };
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList(ALLOWED_ORIGINS));
                configuration.setAllowedMethods(Arrays.asList(
                    HttpMethod.GET.name(),
                    HttpMethod.POST.name(),
                    HttpMethod.PUT.name(),
                    HttpMethod.DELETE.name(),
                    HttpMethod.PATCH.name(),
                    HttpMethod.OPTIONS.name()
                ));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                return configuration;
            }))
            // ? Permite o bloquea la conexión a los endpoints ? //
            .authorizeHttpRequests(authRequest -> {
                authRequest.requestMatchers("/api/v1/mf/auth/**", "/api/v1/mf/oauth2/**").permitAll();
                authRequest.requestMatchers("/favicon.ico", "/error").permitAll();
                authRequest.anyRequest().authenticated();
            })
            // ? Oauth2 Login
            .oauth2Login(oauth2 -> oauth2
                .successHandler((request, response, authentication) -> 
                    response.sendRedirect("/api/v1/mf/auth/oauth2"))
                .failureHandler((request, response, exception) -> 
                    response.sendRedirect("/api/v1/mf/auth/login"))
            )
            // ? Authentication Provider: Es el que se encarga de validar las credenciales de los usuarios ? //
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(ALLOWED_ORIGINS);
            }
        };
    }
}
