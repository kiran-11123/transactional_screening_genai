package com.example.transaction_screening.account.controller;

import javax.management.RuntimeErrorException;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction_screening.account.dto.AccountResponse;
import com.example.transaction_screening.account.dto.CreateAccountRequest;
import com.example.transaction_screening.account.service.AccountService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {

    private AccountService accountService ;

    public AccountController(AccountService accountService){
        this.accountService= accountService;
    }

    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request){
         log.info(
                "Received request to create account for customer ID: {}",
                request.getCustomerId()
        );

        try{

             AccountResponse response =
                    accountService.createAccount(request);

            log.info(
                    "Account creation completed successfully. Account ID: {}",
                    response.getId()
            );

            return response;    

        }
        catch(RuntimeException e){

              log.error(
                    "Account creation failed for customer ID: {}. Error: {}",
                    request.getCustomerId(),
                    e.getMessage(),
                    e
            );

            throw e;

        }
        catch(Exception e){
              
             log.error(
                    "Unexpected error in account creation endpoint",
                    e
            );

           throw new RuntimeException(
                    "Unable to process account creation request",
                    e
            );

        } 
    }

    
    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable Long id){
            log.info(
            "GET /api/accounts/{}",
            id
    );

    try{
        return accountService.getAccountById(id);

        
    }
    catch(Exception e){
         log.error(
                "Failed to fetch account ID: {}. Error: {}",
                id,
                e.getMessage(),
                e
        );

        throw e;
    }
    }
    
    @GetMapping("/number/{accountNumber}")
    public AccountResponse getAccountByNumber(@PathVariable String accountNumber){

         log.info(
            "GET /api/accounts/number/{}",
            accountNumber
    );

     try {

        return accountService.getAccountByNumber(accountNumber);

    } catch (RuntimeException e) {

        log.error(
                "Failed to fetch account number: {}. Error: {}",
                accountNumber,
                e.getMessage(),
                e
        );

        throw e;
    }

          
    }
    
    @GetMapping("/customer/{customerId}")
    public List<AccountResponse> getAccountByCustomerId(@PathVariable Long customerId){

        log.info("GET /api/accounts/customer/{}" , customerId);

        try{
              return accountService.getAccountByCustomerId(customerId);

        }
        catch(RuntimeException e){
              log.error(
                "Failed to fetch accounts for customer ID: {}. Error: {}",
                customerId,
                e.getMessage(),
                e
        );

        throw e;
        }
         
    }



}
