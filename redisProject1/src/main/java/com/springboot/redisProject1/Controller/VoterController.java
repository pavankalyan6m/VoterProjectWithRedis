package com.springboot.redisProject1.Controller;

import com.springboot.redisProject1.Service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class VoterController {
    @Autowired
    private final VoterService voterService;

    @GetMapping("/welcome")
    public String welcome()
    {
        return "This end point is secured....Welcome!.";
    }
    @PostMapping("/rp1/register")
    public VoterAuthenticationResponse registerVoter(@RequestBody VoterRegisterRequest request) {
       System.out.println("Inside registerVoter method");
        return voterService.voterDetailsInsert(request);
    }

    @PostMapping("/rp1/login")
    public VoterAuthenticationResponse loginVoter(@RequestBody VoterAuthenticationRequest request) {
        System.out.println("Inside Login Method");
        return voterService.voterDetailsAuthenticate(request);
    }
}
