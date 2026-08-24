package com.example.transaction_screening.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/health")
@RestController
public class HealthController {
    
    @GetMapping
    public String getHealth(){
          return  "Transaction screening is running";
    }
}
