package com.example.transaction_screening.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction_screening.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/admin")
@RestController
@Slf4j
public class AdminController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> dashBoard(){
          log.info("Entered into the Admin Controller");
          
          

          ApiResponse<String> response = ApiResponse.<String>builder().status(200).message("Admin Access granted").data("Welcome to Admin dashboard").build();
          
          return ResponseEntity.ok(response);
    }

}
