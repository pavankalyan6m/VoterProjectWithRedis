package com.springboot.redisProject1.Repository;

import com.springboot.redisProject1.Entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisTokenRepo implements TokenRepository{
    private final HashOperations<String, String, Token> hashOperations;
    private static final String TOKEN_KEY = "tokens";

    @Autowired
    public RedisTokenRepo(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    private String encodeToken(String token) {
        // Use a consistent encoding for the token (e.g., trim, replace special characters)
        return token;
    }

    public Token findByToken(String token) {
        String encodedToken = encodeToken(token);
        System.out.println("Given Token is: " + encodedToken);
        return hashOperations.get(TOKEN_KEY, encodedToken);
    }

}
