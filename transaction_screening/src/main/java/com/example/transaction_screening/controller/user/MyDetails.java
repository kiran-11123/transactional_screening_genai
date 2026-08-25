package com.example.transaction_screening.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction_screening.dto.ApiResponse;
import com.example.transaction_screening.security.JwtPayloadDetails;

@RestController
@RequestMapping("/api/me")
public class MyDetails {

     @GetMapping
     public ResponseEntity<ApiResponse<String>> getDetails(Authentication authentication){

           JwtPayloadDetails details =
                (JwtPayloadDetails) authentication.getPrincipal();

        String username = details.getUsername();
        String email = details.getEmail();
        String role = details.getRole();

  ApiResponse<String> response =
            ApiResponse.<String>builder()
                    .status(200)
                    .message("User details extracted")
                    .data(
                            "Email: " + email +
                            ", Username: " + username +
                            ", Role: " + role
                    )
                    .build();

    return ResponseEntity.ok(response);
          
     }

}
