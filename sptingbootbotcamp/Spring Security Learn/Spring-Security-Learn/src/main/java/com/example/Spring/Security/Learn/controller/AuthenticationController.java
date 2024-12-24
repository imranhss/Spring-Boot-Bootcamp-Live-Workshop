package com.example.Spring.Security.Learn.controller;

import com.example.Spring.Security.Learn.entity.AuthenticationResponse;
import com.example.Spring.Security.Learn.entity.User;
import com.example.Spring.Security.Learn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user){
        return ResponseEntity.ok(authService.register(user));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>  login(@RequestBody User request){
        return ResponseEntity.ok(authService.authencate(request));

    }

    @GetMapping("/active/{id}")
    public ResponseEntity<String> activeUser(@PathVariable("id") long id){

        String response= authService.activeUser(id);
        return  ResponseEntity.ok(response);
    }




}
