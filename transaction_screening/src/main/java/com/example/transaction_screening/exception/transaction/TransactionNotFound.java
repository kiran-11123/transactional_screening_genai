package com.example.transaction_screening.exception.transaction;

public class TransactionNotFound extends RuntimeException{

    public TransactionNotFound(String message){
        super(message);
    }

}
