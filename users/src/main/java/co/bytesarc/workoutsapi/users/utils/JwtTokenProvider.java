package co.bytesarc.workoutsapi.users.utils;

import co.bytesarc.workoutsapi.users.dto.AuthToken;
import co.bytesarc.workoutsapi.users.exceptions.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiry}")
    private long jwtExpiryInMs;

    @Value("${jwt.refresh-expiry}")
    private long refreshExpiryInMs;


    public AuthToken generateAccessToken(int userId){
        String accessToken = generateToken(userId);
        String refreshToken = generateRefreshToken(userId);
        return  new AuthToken(accessToken,refreshToken);
    }


    private String generateToken(int userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiryInMs);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();
    }

    private String generateRefreshToken(int userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshExpiryInMs);
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();
    }

    public AuthToken refreshToken(String refreshToken) throws InvalidJwtException{
        validateToken(refreshToken);
        int userId = getUserIdFromJWT(refreshToken);
        return generateAccessToken(userId);

    }

    public int getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret.getBytes()).build()
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public void validateToken(String token) throws InvalidJwtException {
        try {
            Jwts.parser().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token);
        } catch (Exception ex) {
            throw new InvalidJwtException();
        }
    }
}
