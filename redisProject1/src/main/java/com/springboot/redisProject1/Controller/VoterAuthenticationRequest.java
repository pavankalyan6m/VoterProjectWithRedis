package com.springboot.redisProject1.Controller;

import com.springboot.redisProject1.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoterAuthenticationRequest {
    private String email;
    String password;
    //Role role;
}
