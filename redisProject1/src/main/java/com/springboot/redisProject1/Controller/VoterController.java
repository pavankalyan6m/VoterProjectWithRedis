package com.springboot.redisProject1.Controller;

import com.springboot.redisProject1.Entity.Voter;
import com.springboot.redisProject1.Service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/rp1/Adminlogin")
    public VoterAuthenticationResponse AdminloginVoter(@RequestBody VoterAuthenticationRequest request) {
        System.out.println("Inside Admin Login Method");
        return voterService.AdminVoterDetailsAuthenticate(request);
    }

    @PostMapping("/rp1/Userlogin")
    public VoterAuthenticationResponse UserloginVoter(@RequestBody VoterAuthenticationRequest request) {
        System.out.println("Inside User Login Method");
        return voterService.UserVoterDetailsAuthenticate(request);
    }
    @GetMapping("/admin/getAllVoters")
    public List<Voter> getAllVoters() {
       System.out.println("Inside getAllVoters Method");
        return voterService.findAll();
    }

    @GetMapping("/admin/findVoter/{id}")
    public Voter findVoter(@PathVariable int id) {
        return voterService.findVoterById(id);
    }

    @DeleteMapping("/admin/deleteVoter/{id}")
    public String remove(@PathVariable int id)   {
        return voterService.deleteVoter(id);
    }
}
