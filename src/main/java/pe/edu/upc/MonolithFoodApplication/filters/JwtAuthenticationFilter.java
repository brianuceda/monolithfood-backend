package pe.edu.upc.MonolithFoodApplication.filters;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException, java.io.IOException {
        
        try {
            // * OBTENCIÓN DE DATOS * //
            final String username;
            final String token = jwtService.getTokenFromRequest(request);
            
            // * VALIDACIÓN DE DATOS * //
            // ? Validar si el Token es nulo o está en la lista negra ? //
            if (token == null) {
                // sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Debe ingresar un token");
                filterChain.doFilter(request, response);
                return;
            }
            if (jwtService.isTokenBlacklisted(token)) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
                return;
            }

            // * PROCESAMIENTO DE DATOS * //
            // ? Obtener el username del token ? //
            username = jwtService.getUsernameFromToken(token);
            
            // * AUTENTICACIÓN DEL TOKEN * //
            // ? Autenticar el Token ? //
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
                    (
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException, java.io.IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"" + message + "\"}");
        response.getWriter().flush();
    }
}
