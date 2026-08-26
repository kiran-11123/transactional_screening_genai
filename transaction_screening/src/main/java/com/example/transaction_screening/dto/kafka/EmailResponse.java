package com.example.transaction_screening.dto.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailResponse {
    
     private String email;
     private Enum status;
}
