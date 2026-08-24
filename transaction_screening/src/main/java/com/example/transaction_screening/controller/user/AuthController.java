package com.example.transaction_screening.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction_screening.dto.ApiResponse;
import com.example.transaction_screening.dto.user.RegisterRequest;
import com.example.transaction_screening.dto.user.RegisterResponse;
import com.example.transaction_screening.service.user.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private AuthService authService;

      public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        try {

            log.info(
                    "Received registration request for username: {}",
                    request.getUsername()
            );

           RegisterResponse result =  authService.registerService(request);

            ApiResponse<RegisterResponse> response =
                    ApiResponse.<RegisterResponse>builder()
                            .status(HttpStatus.CREATED.value())
                            .message("User registered successfully")
                            .data(result)
                            .build();

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (Exception e) {

            log.error(
                    "Error while registering user",
                    e
            );

            throw e;
        }
    }

}
