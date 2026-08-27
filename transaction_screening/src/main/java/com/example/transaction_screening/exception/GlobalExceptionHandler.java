package com.example.transaction_screening.exception;


import lombok.extern.slf4j.Slf4j;

import com.example.transaction_screening.exception.user.InvalidCredentialsException;
import com.example.transaction_screening.exception.user.UserAlreadyExistsException;
import com.example.transaction_screening.exception.user.UserNotFoundException;
import com.example.transaction_screening.exception.account.AccountAlreadyExistsException;
import com.example.transaction_screening.exception.account.AccountNotFoundException;
import com.example.transaction_screening.exception.transaction.InsufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.transaction_screening.exception.transaction.TransactionNotFound;
import com.example.transaction_screening.dto.ApiResponse;
import com.example.transaction_screening.exception.address.AddressNotFoundException;
import com.example.transaction_screening.exception.address.AddressAlreadyExistsException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

  
    

  
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

    
     @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(
            Exception e) {

       
               log.error(
            "User Not found",
            e.getMessage()
    );

        

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("User Not found")
                        .data(null)
                        .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

     @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleAddressNotFound(
            AddressNotFoundException e) {

       
               log.error(
            "Address Not found",
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

@ExceptionHandler(AddressAlreadyExistsException.class)
public ResponseEntity<ApiResponse<Void>> handleAddressAlreadyExists(
        AddressAlreadyExistsException e) {

    log.error(
            "Address already exists: {}",
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


@ExceptionHandler(TransactionNotFound.class)
public ResponseEntity<ApiResponse<Void>> handleTransactionNotFound(TransactionNotFound e){
      log.error("Transaction not found");

    ApiResponse<Void> response = ApiResponse.<Void>builder().status(HttpStatus.NOT_FOUND.value()).message(e.getMessage()).data(null).build();

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

} 


  @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse<Void>> handleInsufficientBalance(InsufficientBalanceException e){
          log.error(
                "Customer not found: {}",
                e.getMessage()
        );
         
         ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(e.getMessage())
                        .data(null)
                        .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


     @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> InvalidCredentails(InvalidCredentialsException e){
          log.error(
                "Customer not found: {}",
                e.getMessage()
        );
         
         ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(e.getMessage())
                        .data(null)
                        .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

     @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> userAlreadyExists(UserAlreadyExistsException e){
          log.error(
                "Customer not found: {}",
                e.getMessage()
        );
         
         ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(e.getMessage())
                        .data(null)
                        .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }



}
