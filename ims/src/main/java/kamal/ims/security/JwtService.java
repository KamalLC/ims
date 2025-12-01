package kamal.ims.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long accessTokenExpMin;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-exp-min: 30}") long accessTokenExpMin) {
        // HS256 secret (use at least 32 chars)
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpMin = accessTokenExpMin;
    }

    public String generateAccessToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .distinct()
                .collect(Collectors.toList());
        claims.put("roles", roles);

         if (user instanceof kamal.ims.user.model.UserPrincipal up) {
             claims.put("userId", up.getUserId());
         }

        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessTokenExpMin * 60);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        try {
            String username = extractUsername(token);
            return username != null
                    && username.equals(user.getUsername())
                    && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }


    public List<String> extractRoles(String token) {
        Object v = parseClaims(token).get("roles");
        if (v instanceof List<?> list) {
            return list.stream().filter(Objects::nonNull).map(Object::toString).toList();
        }
        return Collections.emptyList();
    }

    public boolean isTokenExpired(String token) {
        Date exp = parseClaims(token).getExpiration();
        return exp.before(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
