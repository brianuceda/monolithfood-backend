package xyz.brianuceda.monolithfood_backend.filters;

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

import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;
import xyz.brianuceda.monolithfood_backend.exceptions.BlacklistedTokenException;
import xyz.brianuceda.monolithfood_backend.exceptions.ExceptionConverter;
import xyz.brianuceda.monolithfood_backend.exceptions.NoTokenException;
import xyz.brianuceda.monolithfood_backend.utils.JwtUtils;

@Component
@RequiredArgsConstructor
@SuppressWarnings("null")
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        try {
            final String username;
            final String token = jwtUtils.getTokenFromRequest(request);

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
            if (jwtUtils.isTokenBlacklisted(token)) {
                throw new BlacklistedTokenException();
            }

            username = jwtUtils.getUsernameFromToken(token);

            // Validar si el usuario existe
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtils.isTokenValid(token, userDetails)) {
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
            url.startsWith("/api/v1/auth") ||
            url.startsWith("/auth") ||
            url.startsWith("/oauth") ||
            url.startsWith("/api/v1/oauth2") ||
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
