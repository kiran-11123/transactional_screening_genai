package com.example.transaction_screening.service.transaction;

import org.springframework.stereotype.Service;

import com.example.transaction_screening.dto.transaction.TransactionRequest;
import com.example.transaction_screening.dto.transaction.TransactionResponse;
import com.example.transaction_screening.entity.Transaction;
import com.example.transaction_screening.entity.TransactionStatus;
import com.example.transaction_screening.exception.transaction.TransactionNotFound;
import com.example.transaction_screening.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    } 


   
    public TransactionResponse createTransaction( TransactionRequest request){
         
          log.info(
                "Creating transaction from account {} to account {}",
                request.getSenderAccountId(),
                request.getReceiverAccountId()
        );
        try{

            Transaction transaction  = Transaction.builder().senderAccountId(request.getSenderAccountId()).receiverAccountId(request.getReceiverAccountId()).amount(request.getAmount()).createdAt(LocalDateTime.now()).status(TransactionStatus.PENDING).build();
            Transaction savedTransaction =
                    transactionRepository.save(transaction);

            log.info(
                    "Transaction created successfully with id: {}",
                    savedTransaction.getId()
            );

            return mapToResponse(savedTransaction);
        }
        catch (Exception e) {

            log.error(
                    "Error while creating transaction",
                    e
            );

            throw new RuntimeException(
                    "Unable to create transaction",
                    e
            );
        }
                 
    }


    public TransactionResponse getTransactionById(Long id){
        
         log.info(
                "Fetching transaction with id: {}",
                id
        );
        try{

            Transaction transaction = transactionRepository.findById(id).orElseThrow(()->
        new TransactionNotFound("Transaction with id: "
                                                    + id
                                                    + " not found"));


                        return mapToResponse(transaction);


        }
        catch (RuntimeException e) {

            log.error(
                    "Error while fetching transaction with id {}: {}",
                    id,
                    e.getMessage()
            );

            throw e;

        }
        catch(Exception e){
               log.error(
                    "Unexpected error while fetching transaction {}",
                    id,
                    e
            );

            throw new RuntimeException(
                    "Unable to fetch transaction",
                    e
            );
        }
    }

    public List<TransactionResponse> getAllTransactions(){
         log.info("Fetching all transactions");

         try{

           return  transactionRepository.findAll().stream().map(this :: mapToResponse).toList();

         }
         catch(Exception e){

             log.error(
                    "Error while fetching all transactions",
                    e
            );

            throw new RuntimeException(
                    "Unable to fetch transactions",
                    e
            );

         }
    }


    public TransactionResponse mapToResponse(Transaction transaction){
        return TransactionResponse.builder().id(transaction.getId()).amount(transaction.getAmount()).createdAt(transaction.getCreatedAt()).senderAccountId(transaction.getSenderAccountId()).receiverAccountId(transaction.getReceiverAccountId()).status(transaction.getStatus()).build();
    }

}
