package com.springboot.redisProject1.Controller;

import com.springboot.redisProject1.Entity.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoterRegisterRequest {
    private Integer id;
    private String voterName;
    private String email;
    private String password;
    private Integer voterAge;
    private Role role;
}
