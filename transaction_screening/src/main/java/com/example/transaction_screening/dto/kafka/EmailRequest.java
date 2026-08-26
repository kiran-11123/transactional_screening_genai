package com.example.transaction_screening.dto.kafka;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class EmailRequest {
       @NotBlank(message =  "Email Required")
    private String email;

    @NotBlank(message =  "Idempotent Key is missing")
    private String IdempotentKey;

}
