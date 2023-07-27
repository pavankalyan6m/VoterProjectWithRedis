package com.springboot.redisProject1.Controller;

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
}
