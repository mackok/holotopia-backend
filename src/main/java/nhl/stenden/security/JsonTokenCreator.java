package nhl.stenden.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 * This class provides the JSON token and methods to validate and authenticate the token
 */
public class JsonTokenCreator {

    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private final UserDetailsService myUserDetails;
    private String secretKey;
    private long expiresInMilliseconds = 90909090;


    public JsonTokenCreator(UserDetailsService myUserDetails, String secretKey) {
        this.myUserDetails = myUserDetails;
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    /**
     * This method create a new JSON token
     * @param username The username from the user requesting the token
     * @return
     */
    public String createToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expiresInMilliseconds);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(ALGORITHM, secretKey)
                .compact();
    }


    /**
     * This method authenticate gets the authentication to the user that requested it with the token
     * @param tokenString The string which represents the JSON token
     * @return Gets the authentication to the user that requested it with the token
     */
    public Authentication getAuthentication(String tokenString) {
        Claims claims = getClaims(tokenString);
        String user = claims.getSubject();
        UserDetails userDetails = myUserDetails.loadUserByUsername(user);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }


    /**
     * This method creates a new token to replace the old one that was sent
     * @param tokenString The string which represents the JSON token
     * @return The new JSON token with new expiration date
     */
    public String getRefreshToken(String tokenString) {
        Claims claims = getClaims(tokenString);
        String user = claims.getSubject();
        Date expiration = claims.getExpiration();
        if (new Date(new Date().getTime() + expiresInMilliseconds / 10).after(expiration)) {
            return createToken(user);
        }
        return null;
    }


    /**
     * This method gets the JSON token
     * @param req
     * @return The JSON token
     */
    public String getToken(HttpServletRequest req) {
        String headerBearer = req.getHeader("Authorization");
        if (headerBearer != null && headerBearer.startsWith("Bearer ")) {
            return headerBearer.substring(7);
        }
        return null;
    }


    /**
     * This method validates a JSON token
     * @param tokenString The string which represents the JSON token
     * @return A valid JSON token that can be used to make API request
     */
    public boolean validateToken(String tokenString) {
        Jws<Claims> claimJwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(tokenString);
        return SignatureAlgorithm.forName(claimJwt.getHeader().getAlgorithm()) == ALGORITHM;
    }


    /**
     * This method gets the claim on a JSON token
     * @param tokenString The string which represents the JSON token
     * @return the claims of the JSON token
     */
    private Claims getClaims(String tokenString) {
        Jws<Claims> claimJwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(tokenString);
        return claimJwt.getBody();
    }

}
