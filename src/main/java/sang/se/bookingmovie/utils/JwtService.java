package sang.se.bookingmovie.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sang.se.bookingmovie.exception.AllException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.auth_expiration}")
    private Long authExpiration;

    @Value("${verify.verify_expiration}")
    private Long verifyExpiration;

    public String extractSubject(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + authExpiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateVerify(String userId, String verifyCode) {
        return generateVerify(new HashMap<>(), userId, verifyCode);
    }

    public String generateVerify(
            Map<String, Object> extraClaims,
            String userId,
            String verifyCode
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userId + "_" + verifyCode)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + verifyExpiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractSubject(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String validateToken(String token) {
        if(token == null || !token.startsWith("Bearer ")) {
            throw new AllException("Unauthorized", 401, List.of("Unauthorized"));
        } else {
            token = token.substring(7);
            return token;
        }
    }
}
