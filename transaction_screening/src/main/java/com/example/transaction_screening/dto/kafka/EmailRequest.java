package com.example.transaction_screening.dto.kafka;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailRequest {
       @NotBlank(message =  "Email Required")
    private String email;

    @NotBlank(message =  "Idempotent Key is missing")
    private String IdempotentKey;

}
