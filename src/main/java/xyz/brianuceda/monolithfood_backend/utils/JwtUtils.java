package xyz.brianuceda.monolithfood_backend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// import java.security.Key;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.Map;
// import java.util.Set;
// import java.util.function.Function;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpHeaders;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;
// import org.springframework.util.StringUtils;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;
// import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtils {
    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    @Value("${JWT_EXPIRATION}")
    private long expiration;

}
