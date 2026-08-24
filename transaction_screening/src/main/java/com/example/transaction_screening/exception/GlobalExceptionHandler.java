package com.example.transaction_screening.exception;
import com.example.transaction_screening.exception.customer.CustomerAlreadyExistsException;
import com.example.transaction_screening.exception.customer.CustomerNotFoundException;

import lombok.extern.slf4j.Slf4j;

import com.example.transaction_screening.exception.account.AccountAlreadyExistsException;
import com.example.transaction_screening.exception.account.AccountNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.transaction_screening.dto.ApiResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

     
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomerNotFound(CustomerNotFoundException e){
          log.error(
                "Customer not found: {}",
                e.getMessage()
        );
         
         ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(e.getMessage())
                        .data(null)
                        .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> CustomerAlreadyExists(CustomerAlreadyExistsException e){
          
          ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.CONFLICT.value())
                        .message(e.getMessage())
                        .data(null)
                        .build();
          
                        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

     @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(
            Exception e) {

        log.error(
                "Unexpected error occurred",
                e
        );

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Something went wrong")
                        .data(null)
                        .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
    
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccountNotFound(Exception e){

        log.error(
            "Account not found: {}",
            e.getMessage()
    );

    ApiResponse<Void> response =
            ApiResponse.<Void>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage())
                    .data(null)
                    .build();

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response);
         
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
public ResponseEntity<ApiResponse<Void>> handleAccountAlreadyExists(
        AccountAlreadyExistsException e) {

    log.error(
            "Account already exists: {}",
            e.getMessage()
    );

    ApiResponse<Void> response =
            ApiResponse.<Void>builder()
                    .status(HttpStatus.CONFLICT.value())
                    .message(e.getMessage())
                    .data(null)
                    .build();

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(response);
}

}
