package com.example.transaction_screening.exception.transaction;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message){
         super(message);
    }

}
