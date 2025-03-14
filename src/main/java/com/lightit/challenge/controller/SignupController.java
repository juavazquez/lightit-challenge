package com.lightit.challenge.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class SignupController {
    
    @PostMapping("/signup")
    public String postMethodName(@RequestBody String entity) {
        // TODO: Implement this method
        
        return entity;
    }
    
}
