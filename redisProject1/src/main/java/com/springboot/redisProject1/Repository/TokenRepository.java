package com.springboot.redisProject1.Repository;

import com.springboot.redisProject1.Entity.Token;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface TokenRepository {

//    List<Token> findAllValidTokenByUser(Integer id);

    Token findByToken(String token);
}
