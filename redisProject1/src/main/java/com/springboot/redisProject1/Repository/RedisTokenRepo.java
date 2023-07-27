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


    @Override
    public <S extends String> S save(S entity) {
        return null;
    }

    @Override
    public <S extends String> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<String> findById(Token token) {
        // Assuming the token itself is the ID, we can simply return it as a string
        return Optional.ofNullable(token.getToken());
    }
    @Override
    public boolean existsById(Token token) {
        return false;
    }

    @Override
    public Iterable<String> findAll() {
        return null;
    }

    @Override
    public Iterable<String> findAllById(Iterable<Token> tokens) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Token token) {

    }

    @Override
    public void delete(String entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Token> tokens) {

    }

    @Override
    public void deleteAll(Iterable<? extends String> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
