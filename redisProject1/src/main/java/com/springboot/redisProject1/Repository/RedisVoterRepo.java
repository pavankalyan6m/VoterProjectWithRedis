package com.springboot.redisProject1.Repository;


import com.springboot.redisProject1.Entity.Role;
import com.springboot.redisProject1.Entity.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisVoterRepo implements VoterRepository {

    public static final String HASH_KEY = "voters";
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Voter> hashOperations;

    private Integer getNextId() {
        Long count = hashOperations.size(HASH_KEY);
        return count.intValue() + 1;
    }
    @Autowired
    public RedisVoterRepo(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Voter save(Voter voter) {
        System.out.println("Inside Save User Method!...");
        String id = String.valueOf(voter.getId()); // Convert the ID to String
        String roleGiven = String.valueOf(voter.getRole());
        if (roleGiven.equals("ADMIN") || roleGiven.equals("USER"))
        {
            System.out.println("Id Fetched is: " + id);
            if (id != null) {
                hashOperations.put(HASH_KEY, id, voter);
            } else {
                System.err.println("Cannot save voter with null ID!");
            }
        }
        else{
            System.err.println("Cannot save voter with Invalid Role, The Role must be Either USER or ADMIN!");
        }
        return voter;
    }

    public Voter findVoterByEmail(String email) {
        System.out.println("Inside findVoterByEmail Method:");
        System.out.println("Email passed is: "+email);
        Iterable<Voter> voters = hashOperations.values(HASH_KEY);
        System.out.println("Voters Details:"+voters);
        for (Voter voter : voters) {
            if (email.equals(voter.getEmail())) {
                return voter;
            }
        }
        // Return an empty Optional if no voter is found with the given email
        System.out.println("User Not found");
        return null;
    }

    @Override
    public Optional<Voter> findById(Integer id) {
        return Optional.ofNullable(hashOperations.get(HASH_KEY, id));
    }

    public String getRoleOfUser(String email) {
        Voter voter_details_from_email = findVoterByEmail(email);
        return voter_details_from_email.getRole().toString();
    }
}
