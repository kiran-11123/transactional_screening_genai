package com.example.transaction_screening.controller.customer;

import java.util.concurrent.ExecutionException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction_screening.dto.ApiResponse;
import com.example.transaction_screening.dto.Customer.CustomerRequest;
import com.example.transaction_screening.dto.Customer.CustomerResponse;
import com.example.transaction_screening.security.JwtPayloadDetails;
import com.example.transaction_screening.service.customer.CustomerService;

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
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest request, @AuthenticationPrincipal JwtPayloadDetails userDetails){
           
        try{
             log.info(
                    "Received request to create customer with email: {}",
                    request.getEmail()
            );

    Long userId = userDetails.getId();

    String email = userDetails.getEmail();

  

    log.info(
            "Authenticated user: id={}, email={}",
            userId,
            email
    );
            CustomerResponse result =  customerService.createCustomer(request , userId );
            
                ApiResponse<CustomerResponse> response = ApiResponse.<CustomerResponse>builder()
                    .status(200)
                    .message("Customer Created successfully")
                    .data(result)
                    .build();

            return ResponseEntity.ok(response);


        }
        catch(Exception e){
            log.error("Error in createCustomer controller", e);
            throw e;
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable Long id){
        
        try{

             log.info(
                    "Received request to fetch customer with id: {}",
                    id
            );

            CustomerResponse result= customerService.getCustomerById(id);
            ApiResponse<CustomerResponse> response = ApiResponse.<CustomerResponse>builder().status(200).message("Customer data Fetched successfully").data(result).build();
            return ResponseEntity.ok(response);

        }
        catch(Exception e){
              log.error(
                    "Error in getCustomerById controller for id: {}",
                    id,
                    e
            );

            throw e;
        }
          
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers(){

        try{
            log.info(
                    "Received request to fetch all customers "
            );

                List<CustomerResponse> result = customerService.getAllCustomers();

                ApiResponse<List<CustomerResponse>> response = ApiResponse.<List<CustomerResponse>>builder()
                    .status(200)
                    .message("Customer data Fetched successfully")
                    .data(result)
                    .build();

                return ResponseEntity.ok(response);

            

        }
        catch(Exception e){
            log.error("Error in getAllCustomers controller", e);
            throw e;
        }

    }



}
