package com.example.transaction_screening.controller.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.transaction_screening.dto.transaction.TransactionResponse;
import com.example.transaction_screening.dto.transaction.TransactionRequest;
import com.example.transaction_screening.dto.ApiResponse;
import com.example.transaction_screening.service.transaction.TransactionService;
import java.util.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
         this.transactionService = transactionService; 
    }
   
    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(@Valid @RequestBody TransactionRequest request){
          
          log.info(
                    "Received request to create transaction from account {} to account {}",
                    request.getSenderAccountId(),
                    request.getReceiverAccountId()
            );
        try{

            TransactionResponse result =
                    transactionService.createTransaction(request);

            ApiResponse<TransactionResponse> response =
                    ApiResponse.<TransactionResponse>builder()
                            .status(201)
                            .message("Transaction created successfully")
                            .data(result)
                            .build();

            return ResponseEntity
                    .status(201)
                    .body(response);

        }
        catch(Exception e){
              log.error(
                    "Error in createTransaction controller",
                    e
            );

            throw e;
        }
    }
     @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>>
            getTransactionById(
                    @PathVariable Long id) {

        try {

            log.info(
                    "Received request to fetch transaction with id: {}",
                    id
            );

            TransactionResponse result =
                    transactionService.getTransactionById(id);

            ApiResponse<TransactionResponse> response =
                    ApiResponse.<TransactionResponse>builder()
                            .status(200)
                            .message("Transaction fetched successfully")
                            .data(result)
                            .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            log.error(
                    "Error in getTransactionById controller for id: {}",
                    id,
                    e
            );

            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionResponse>>>
            getAllTransactions() {

        try {

            log.info("Received request to fetch all transactions");

            List<TransactionResponse> result =
                    transactionService.getAllTransactions();

            ApiResponse<List<TransactionResponse>> response =
                    ApiResponse.<List<TransactionResponse>>builder()
                            .status(200)
                            .message("Transactions fetched successfully")
                            .data(result)
                            .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            log.error(
                    "Error in getAllTransactions controller",
                    e
            );

            throw e;
        }
    }

}
