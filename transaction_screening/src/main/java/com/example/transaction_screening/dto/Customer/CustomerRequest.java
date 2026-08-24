package com.example.transaction_screening.dto.Customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    
    @NotBlank(message = "Name is Required")
    @Size(min =  3 , max = 20 , message = "Name must between 3 and 20 characters")
    private String name;

    
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email format")
    private String email;
      
    @NotBlank(message = "Phone is requried")
    @Pattern(regexp = "^[0-9]{10}$",
        message = "Phone must contain exactly 10 digits")
    private String phone;  


}
