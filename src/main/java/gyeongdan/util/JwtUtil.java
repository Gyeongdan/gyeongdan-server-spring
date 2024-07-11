package gyeongdan.util;

import gyeongdan.user.domain.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;
    private static final long ACCESS_TOKEN_VALIDITY_MINUTES = 15;
    private static final long REFRESH_TOKEN_VALIDITY_DAYS = 30;

    public TokenResponse generateTokenResponse(Users user) {
        return TokenResponse.builder()
            .accessToken(generateAccessToken(user))
            .refreshToken(generateRefreshToken(user))
            .build();
    }

    public String generateAccessToken(Users user) {
        return generateToken(user, ACCESS_TOKEN_VALIDITY_MINUTES);
    }

    public String generateRefreshToken(Users user) {
        return generateToken(user, REFRESH_TOKEN_VALIDITY_DAYS * 24 * 60);
    }

    public String generateToken(Users user, Long validityMinutes) {
        LocalDateTime now = LocalDateTime.now();
        Date issuedAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date expiryDate = Date.from(
            now.plusMinutes(validityMinutes).atZone(ZoneId.systemDefault()).toInstant());

        Claims claims = Jwts.claims()
            .subject(user.getId().toString())
            .add("id", user.getId())
            .add("name", user.getName())
            .add("kakaoUserId", user.getKakaoUserId())
            .add("role", user.getRole())
            .build();

        Key key = generateKey();

        return Jwts.builder()
            .claims(claims)
            .issuedAt(issuedAt)
            .expiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }


    public SecretKey generateKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(generateKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(generateKey()).build().parseClaimsJws(token).getBody();
        return (Long) claims.get("id");
    }


    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(generateKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getKakaoUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(generateKey()).build().parseClaimsJws(token).getBody();
        return (String) claims.get("kakaoUserId");
    }

    public String getRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(generateKey()).build().parseClaimsJws(token).getBody();
        return (String) claims.get("role");
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
