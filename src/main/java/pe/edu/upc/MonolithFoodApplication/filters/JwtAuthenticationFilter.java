package pe.edu.upc.MonolithFoodApplication.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
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
import pe.edu.upc.MonolithFoodApplication.exceptions.BlacklistedTokenException;
import pe.edu.upc.MonolithFoodApplication.exceptions.NoTokenException;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    // Log para mostrar errores en la consola
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {

        try {
            // Obtener el token de la cabecera
            final String username;
            final String token = jwtService.getTokenFromRequest(request);
            // Si no se manda un token y la URL es pública, se continúa con la cadena de
            // filtros (el usuario está iniciando sesión o registrándose)
            if (token == null && isPublicUrl(request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            } else if (token == null) {
                throw new NoTokenException();
            }
            // Si el token está en la lista negra, se rechaza la solicitud
            if (jwtService.isTokenBlacklisted(token)) {
                throw new BlacklistedTokenException();
            }
            // Obtener el username del token
            username = jwtService.getUsernameFromToken(token);
            // Validar si el username es nulo o si el usuario ya está autenticado
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
            saveAndShowInfoError(e, response,
                    new ResponseDTO("Se necesita un token.", HttpStatus.UNAUTHORIZED.value()));
        } catch (BlacklistedTokenException e) {
            saveAndShowInfoError(e, response,
                    new ResponseDTO("El token fue inhabilitado.", HttpStatus.UNAUTHORIZED.value()));
        } catch (SignatureException | MalformedJwtException e) {
            saveAndShowInfoError(e, response,
                    new ResponseDTO("El token es invalido.", HttpStatus.UNAUTHORIZED.value()));
        } catch (ExpiredJwtException e) {
            saveAndShowInfoError(e, response,
                    new ResponseDTO("El token ha expirado.", HttpStatus.UNAUTHORIZED.value()));
        } catch (UsernameNotFoundException e) {
            saveAndShowInfoError(e, response,
                    new ResponseDTO("Nombre de usuario o contraseña inválidos.", HttpStatus.UNAUTHORIZED.value()));
        } catch (Exception e) {
            saveAndShowInfoError(e, response,
                    new ResponseDTO("Ocurrió un error interno.", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    // Reemplazar por el Entry Point cuando se haga
    private void sendErrorResponseInJSON(HttpServletResponse response, ResponseDTO responseDTO)
            throws IOException, java.io.IOException {
        response.setStatus(responseDTO.getStatusCode());
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"" + responseDTO.getMessage() + "\",\"statusCode\":"
                + responseDTO.getStatusCode() + "}");
        response.getWriter().flush();
    }

    // Guarda el error en el log, muestra el mensaje
    private void saveAndShowInfoError(Exception e, HttpServletResponse response, ResponseDTO responseDTO)
            throws IOException, java.io.IOException {
        logger.error("Entrando al bloque Exception: " + e.getClass().getName());
        logger.error("Causa exacta: " + e.getCause());
        sendErrorResponseInJSON(response, responseDTO);
    }

    // Si la url es pública, retorna true
    private boolean isPublicUrl(String url) {
        return url.equals("/auth/login") ||
                url.equals("/auth/register") ||
                url.equals("/auth/oauth2") ||
                url.equals("/auth/oauth2/callback") ||
                url.equals("/auth/oauth2/callback/github");
    }

}
