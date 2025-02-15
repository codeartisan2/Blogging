package com.codeartisan.blog_app.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service

public class JwtTokenHelper {
//    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private final long jwtExpirationInMs = 3600000;
//    private String secretKey = "jwtTokenKey";
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .verifyWith((SecretKey) secretKey) // Use a byte array for the signing key
                    .build();
            Jws<Claims> claimsJws = parser.parseSignedClaims(token);
            return claimsJws.getPayload();
        } catch (SignatureException e) {
            // Handle invalid signature
            throw new RuntimeException("Invalid JWT signature", e);
        }

    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
//        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Set custom claims
                .setSubject(subject) // Set the subject
                .setIssuedAt(new Date()) // Set issue date
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)) // Set expiration
                .signWith(secretKey) // Sign the token with the secret key
                .compact(); // Build and return the token
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = getUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


}
