package com.springboot.redisProject1.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoterAuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
