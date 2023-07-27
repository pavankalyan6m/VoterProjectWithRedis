package com.springboot.redisProject1.Repository;

import com.springboot.redisProject1.Entity.Voter;

import java.util.Optional;

public interface VoterRepository {
    Voter save(Voter voter);
    Voter findVoterByEmail(String email);
    Optional<Voter> findById(Integer id);
}
