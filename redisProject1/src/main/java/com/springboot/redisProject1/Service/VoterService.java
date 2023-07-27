package com.springboot.redisProject1.Service;

import com.springboot.redisProject1.Configuration.VoterJwtService;
import com.springboot.redisProject1.Controller.VoterAuthenticationRequest;
import com.springboot.redisProject1.Controller.VoterAuthenticationResponse;
import com.springboot.redisProject1.Controller.VoterRegisterRequest;
import com.springboot.redisProject1.Entity.Role;
import com.springboot.redisProject1.Entity.Voter;
import com.springboot.redisProject1.Repository.RedisVoterRepo;
import com.springboot.redisProject1.Repository.TokenRepository;
import com.springboot.redisProject1.Repository.VoterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoterService {
    private final RedisVoterRepo redisVoterRepo;
    private final PasswordEncoder passwordEncoder;
    private final VoterJwtService voterJwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    private static final String VOTER_KEY = "voters";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Voter> hashOperations;

    @Autowired
    public VoterService(RedisVoterRepo redisVoterRepo,
                        PasswordEncoder passwordEncoder,
                        VoterJwtService voterJwtService,
                        VoterJwtService voterJwtService1, AuthenticationManager authenticationManager,
                        TokenRepository tokenRepository,
                        RedisTemplate<String, Object> redisTemplate) {
        this.redisVoterRepo = redisVoterRepo;
        this.passwordEncoder = passwordEncoder;
        this.voterJwtService = voterJwtService1;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }
    public VoterAuthenticationResponse voterDetailsInsert(VoterRegisterRequest request) {
        var user = Voter.builder()
                .id(request.getId())
                .voterName(request.getVoterName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .voterAge((Integer) request.getVoterAge())
                .role(Role.USER)
                .build();

        var savedUser = redisVoterRepo.save(user);
        var jwtToken = voterJwtService.generateToken(savedUser); // Use the savedUser directly as UserDetails

        return VoterAuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public VoterAuthenticationResponse voterDetailsAuthenticate(VoterAuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = redisVoterRepo.findVoterByEmail(request.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        var jwtToken = voterJwtService.generateToken(user);

        return VoterAuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private List<SimpleGrantedAuthority> getAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }
}
