package pe.edu.upc.MonolithFoodApplication.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;

@Service
public class JwtService {
    // ? Atributos
    // Variables de entorno
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.time.expiration}")
    private String TIME_EXPIRATION;
    // Blacklist de Tokens
    private Set<String> memoryBackendBlacklistedTokens = new HashSet<>();

    // ? Métodos
    // Obtener el token de una solicitud HTTP
    public String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            return null;
        }
    }
    // Generar un Token JWT en los datos de un usuario
    public String genToken(UserDetails user, String profileStage) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("profileStage", profileStage);
        return genToken(extraClaims, user);
    }
    private String genToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(TIME_EXPIRATION)))
                .signWith(genTokenSign(), SignatureAlgorithm.HS256)
                .compact();
    }
    // Firmar el Token con una clave privada
    private Key genTokenSign() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    // Validar la existencia del Token JWT
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    // Validar la expiración del Token JWT
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
    // Validar si el Token está en la Blacklist
    public void addTokenToBlacklist(String token) {
        memoryBackendBlacklistedTokens.add(token);
    }
    public boolean isTokenBlacklisted(String token) {
        return memoryBackendBlacklistedTokens.contains(token);
    }
    // Obtener la información del Token JWT (información que se puede obtener sin la clave privada)
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(genTokenSign())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }
    public String getUsernameFromBearerToken(String bearerToken) {
        String realToken = bearerToken.replace("Bearer ", "");
        return getUsernameFromToken(realToken);
    }
    public String getRealToken(String bearerToken) {
        String realToken = bearerToken.replace("Bearer ", "");
        return realToken;
    }
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }
	// My methods
    public String determineProfileStage(UserEntity user) {
        return
            user.getUserPersonalInfo() == null || user.getUserFitnessInfo() == null ? "information" :
            user.getUserPersonalInfo().getActivityLevel() == null ? "activity-level" :
            user.getObjectives() == null || user.getObjectives().isEmpty() ? "objectives" :
            "completed";
    }
}
