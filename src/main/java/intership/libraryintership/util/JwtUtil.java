package intership.libraryintership.util;

import intership.libraryintership.dto.jwt.JwtDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final long tokenLiveTime = 1000L * 60 * 60 * 24 * 7; // 1 week
    private static final long refreshTokenLiveTime = 1000L * 60 * 60 * 24 * 30; // 1 month
    private static final String secretKey = "winnerWinnerChickenDinnerAgarBoshBo'shBo'lsaUniTo'ldirishKerakBo'lmasaKichrayishniBoshlaydi";

    public static String encode(String login, String role){
        Map<String, Object> extraClaims  = new HashMap<>();
        extraClaims.put("role", role);
        extraClaims.put("login", login);

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String refreshToken(String login, String role){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);
        extraClaims.put("login", login);

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenLiveTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static JwtDTO decode(String token){
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String role = (String) claims.get("role");
            String login = (String) claims.get("login");

            return new JwtDTO(role, login);
    }

    public static TokenValidationResult validateToken(String token){
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            Date now = new Date();

            if (expiration.before(now)) {
                return new TokenValidationResult(false, "Token is expired");
            }
            return new TokenValidationResult(true, "Token is valid");
        }catch (ExpiredJwtException e){
            return new TokenValidationResult(false, "Token is expired");
        }catch (MalformedJwtException m){
            return new TokenValidationResult(false, "Invalid token format");
        }catch (Exception e){
            return new TokenValidationResult(false, "Invalid token");
        }
    }

    private static Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public static class TokenValidationResult{
        private final boolean valid;
        private final String message;

        public TokenValidationResult(boolean valid, String message){
            this.valid = valid;
            this.message = message;
        }

        public boolean isValid(){
            return valid;
        }

        public String getMessage(){
            return message;
        }





    }
}
