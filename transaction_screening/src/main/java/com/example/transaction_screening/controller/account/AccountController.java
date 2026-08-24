package com.example.transaction_screening.controller.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.example.transaction_screening.dto.ApiResponse;
import com.example.transaction_screening.dto.Account.AccountRequest;
import com.example.transaction_screening.dto.Account.AccountResponse;
import com.example.transaction_screening.service.account.AccountService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService=accountService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountRequest request){
          
        try{

            log.info(
                    "Received request to create account: {}",
                    request.getAccountNumber()
            );

            AccountResponse result = accountService.createAccount(request);

            ApiResponse<AccountResponse> response =  ApiResponse.<AccountResponse>builder().status(HttpStatus.CREATED.value())
                            .message("Account created successfully")
                            .data(result)
                            .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }
        catch(Exception e){
              log.error(
                    "Error in createAccount controller",
                    e
            );

            throw e;
        }
    }

         @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable Long id){
             
                    try {

            log.info(
                    "Received request to fetch account with id: {}",
                    id
            );

            AccountResponse result =
                    accountService.getAccountById(id);

            ApiResponse<AccountResponse> response =
                    ApiResponse.<AccountResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("Account fetched successfully")
                            .data(result)
                            .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            log.error(
                    "Error in getAccountById controller for id: {}",
                    id,
                    e
            );

            throw e;
        }



        }


        @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<AccountResponse>>>
            getAccountsByCustomerId(
                    @PathVariable Long customerId) {

        try {

            log.info(
                    "Received request to fetch accounts for customer: {}",
                    customerId
            );

            List<AccountResponse> result =
                    accountService.getAccountsByCustomerId(
                            customerId
                    );

            ApiResponse<List<AccountResponse>> response =
                    ApiResponse.<List<AccountResponse>>builder()
                            .status(HttpStatus.OK.value())
                            .message(
                                    "Customer accounts fetched successfully"
                            )
                            .data(result)
                            .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            log.error(
                    "Error in getAccountsByCustomerId controller for customer: {}",
                    customerId,
                    e
            );

            throw e;
        }
    }
             


}
