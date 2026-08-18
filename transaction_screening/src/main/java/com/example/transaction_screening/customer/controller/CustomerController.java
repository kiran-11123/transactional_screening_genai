package com.example.transaction_screening.customer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction_screening.customer.dto.CreateCustomerRequest;
import com.example.transaction_screening.customer.dto.CustomerResponse;
import com.example.transaction_screening.customer.service.CustomerService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
       
    
    @PostMapping
    public ResponseEntity<CustomerResponse>  createCustomer(@Valid @RequestBody CreateCustomerRequest request){
          log.info(
            "Received request to create customer with email: {}",
            request.getEmail()
        );

        try{

            CustomerResponse response = customerService.createCustomer(request);
            return  ResponseEntity.ok(response);

        }
        catch(Exception e){

             log.error(
                "Unexpected error in customer creation endpoint",
                e
            );

            throw new RuntimeException(
                "Unable to process customer creation request",
                e
            );
             
        }
    }
}
