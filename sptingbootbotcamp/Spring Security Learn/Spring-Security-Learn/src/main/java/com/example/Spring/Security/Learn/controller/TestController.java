package com.example.Spring.Security.Learn.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {


    @GetMapping("/get")
    public ResponseEntity<String> activeUser(){


        return  ResponseEntity.ok("Response Successful");
    }

}
