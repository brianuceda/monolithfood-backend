package pe.edu.upc.MonolithFoodApplication.filters;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.SignatureException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.exceptions.BlacklistedTokenException;
import pe.edu.upc.MonolithFoodApplication.exceptions.ExceptionConverter;
import pe.edu.upc.MonolithFoodApplication.exceptions.NoTokenException;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;

@Component
@RequiredArgsConstructor
@SuppressWarnings("null")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        try {
            final String username;
            final String token = jwtService.getTokenFromRequest(request);

            // Está iniciando sesión o registrándose
            if (token == null) {
                if (isPublicUrl(request.getRequestURI())) {
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    throw new NoTokenException("No token provided");
                }
            }

            // Token en blacklist
            if (jwtService.isTokenBlacklisted(token)) {
                throw new BlacklistedTokenException();
            }

            username = jwtService.getUsernameFromToken(token);

            // Validar si el usuario existe
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Continuar con la cadena de filtros
            filterChain.doFilter(request, response);
        } catch (NoTokenException e) {
            ExceptionConverter.saveAndShowInfoError(e, response,
                new ResponseDTO("Se necesita un token", 401, ResponseType.ERROR));
        } catch (BlacklistedTokenException e) {
            ExceptionConverter.saveAndShowInfoError(e, response,
                new ResponseDTO("Es token fue inhabilitado", 401, ResponseType.ERROR));
        } catch (SignatureException | MalformedJwtException e) {
            ExceptionConverter.saveAndShowInfoError(e, response,
                new ResponseDTO("El token es invalido", 401, ResponseType.ERROR));
        } catch (ExpiredJwtException e) {
            ExceptionConverter.saveAndShowInfoError(e, response,
                new ResponseDTO("El token expiradó", 401, ResponseType.ERROR));
        } catch (UsernameNotFoundException e) {
            ExceptionConverter.saveAndShowInfoError(e, response,
                new ResponseDTO("Datos inválidos", 401, ResponseType.ERROR));
        } catch (Exception e) {
            ExceptionConverter.saveAndShowInfoError(e, response,
                new ResponseDTO("Ocurrió un error interno", 500, ResponseType.ERROR));
        }
    }

    // Si la url es pública, retorna true
    private boolean isPublicUrl(String url) {
        return
            url.startsWith("/auth") ||
            url.startsWith("/oauth") ||
            url.startsWith("/login") ||
            url.startsWith("/logout") ||
            // Oauth2
            url.startsWith("/error") ||
            url.contains("/favicon.ico") ||
            // Swagger
            url.startsWith("/v3/api-docs") ||
            url.startsWith("/doc/swagger-ui");
    }

}
