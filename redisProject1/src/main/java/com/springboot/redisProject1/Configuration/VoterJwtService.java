package com.springboot.redisProject1.Configuration;

import com.springboot.redisProject1.Entity.Token;
import com.springboot.redisProject1.Repository.RedisTokenRepo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class VoterJwtService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY ;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private static final String TOKEN_KEY = "tokens";

    private final RedisTokenRepo tokenRepository;

    @Autowired
    public VoterJwtService(RedisTemplate<String, Object> redisTemplate, RedisTokenRepo tokenRepository) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.tokenRepository = tokenRepository;
    }
    public String extractUsername(String token) {
        System.out.println("Inside ExtractUserName...");
        System.out.println("the details fetched are"+extractClaim(token, Claims::getSubject));
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        String token = generateToken(new HashMap<>(), userDetails);
        saveToken(userDetails.getUsername(), token);
        return token;
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println("Inside extractClaim method...");
        final Claims claims = extractAllClaims(token);
        System.out.println("Claims fetched are: "+claims);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userEmail = extractUsername(token);
        System.out.println("User Email inside isTokenValid is: "+userEmail);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        try {
            System.out.println("is token Expired method: "+ extractExpiration(token).before(new Date()));
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return true;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Date extractExpiration(String token)
    {
        System.out.println("Inside extractExpiration...."+extractClaim(token, Claims::getExpiration));
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        //String cleanedToken = token.replaceAll("\\s+", "");
        try {
            System.out.println("Inside extractAllClaims: "+token);
            return Jwts
                    .parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
            // Handle the expired token error (e.g., log, return null, etc.)
            return null;
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT token: " + e.getMessage());
            // Handle the malformed token error (e.g., log, return null, etc.)
            return null;
        } catch (JwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            // Handle other JWT parsing errors (e.g., log, return null, etc.)
            return null;
        }
    }

    //haven't used this method
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Save the generated token into the "tokens" key
    public void saveToken(String username, String token) {
        System.out.println("Inside Save token Method");
        hashOperations.put(TOKEN_KEY, username, token);
    }

    // Retrieve the token from Redis using the username as the key
    private String getTokenFromRedis(String username) {
        return hashOperations.get(TOKEN_KEY, username);
    }
}

