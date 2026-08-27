package com.example.transaction_screening.service.transaction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.transaction_screening.dto.kafka.TransactionEvent;
import com.example.transaction_screening.dto.transaction.TransactionRequest;
import com.example.transaction_screening.dto.transaction.TransactionResponse;
import com.example.transaction_screening.entity.Account;
import com.example.transaction_screening.entity.Transaction;
import com.example.transaction_screening.entity.TransactionStatus;
import com.example.transaction_screening.exception.account.AccountNotFoundException;
import com.example.transaction_screening.exception.transaction.InsufficientBalanceException;
import com.example.transaction_screening.exception.transaction.TransactionNotFound;
import com.example.transaction_screening.repository.AccountRepository;
import com.example.transaction_screening.repository.TransactionRepository;
import com.example.transaction_screening.service.kafka.TransactionProducerService;
import com.example.transaction_screening.repository.UserRepository;
import com.example.transaction_screening.entity.User;
import lombok.extern.slf4j.Slf4j;
import com.example.transaction_screening.exception.user.UserNotFoundException;
import com.example.transaction_screening.exception.address.AddressNotFoundException;

@Service
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionProducerService transactionProducerService;
    private final UserRepository userRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository , TransactionProducerService transactionProducerService , UserRepository userRepository) {
        this.userRepository=userRepository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionProducerService = transactionProducerService;
    }

    // CREATE TRANSACTION
    @Transactional
    public TransactionResponse createTransaction(
            TransactionRequest request) {

        log.info(
                "Creating transaction from account {} to account {}",
                request.getSenderAccountId(),
                request.getReceiverAccountId()
        );

        try {

                User SenderUser= getUser(request.getSenderAccountId());
                User receiverUser = getUser(request.getReceiverAccountId());

                if(SenderUser.getAddress() == null ){

                        throw new AddressNotFoundException("Sender Address not found..");
        
                }
                if(receiverUser.getAddress() == null){
                         throw new AddressNotFoundException("Reciever Address not found..");
                }

            // Find sender account
            Account senderAccount = accountRepository
                    .findById(request.getSenderAccountId())
                    .orElseThrow(() ->
                            new AccountNotFoundException(
                                    "Sender account not found: "
                                            + request.getSenderAccountId()
                            )
                    );

            // Find receiver account
            Account receiverAccount = accountRepository
                    .findById(request.getReceiverAccountId())
                    .orElseThrow(() ->
                            new AccountNotFoundException(
                                    "Receiver account not found: "
                                            + request.getReceiverAccountId()
                            )
                    );

            if (senderAccount.getId().equals(receiverAccount.getId())) {
            throw new RuntimeException(
                    "Sender and receiver accounts cannot be the same"
            );
        }
          
        if(senderAccount.getBalance().compareTo(request.getAmount()) < 0){
             throw new InsufficientBalanceException(
                    "Insufficient balance in sender account"
            );
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(request.getAmount()));

        receiverAccount.setBalance(receiverAccount.getBalance().add(request.getAmount()));

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

            // Create transaction
            Transaction transaction = Transaction.builder()
                    .senderAccount(senderAccount)
                    .receiverAccount(receiverAccount)
                    .amount(request.getAmount())
                    .createdAt(LocalDateTime.now())
                    .status(TransactionStatus.COMPLETED)
                    .build();

            // Save transaction
            Transaction savedTransaction =
                    transactionRepository.save(transaction);


            TransactionEvent rEvent = TransactionEvent.builder().createdAt(savedTransaction.getCreatedAt()).amount(savedTransaction.getAmount()).receiverAccountId(savedTransaction.getReceiverAccount().getId()).senderAccountId(savedTransaction.getSenderAccount().getId()).status(savedTransaction.getStatus().name()).transactionId(savedTransaction.getId()).build();

           log.info("Sending Transaction event to KafkaProducer {} ", rEvent.getTransactionId());
           transactionProducerService.sendTransactionEvent(rEvent);

            log.info(
                    "Transaction created successfully with id: {}",
                    savedTransaction.getId()
            );

            return mapToResponse(savedTransaction);

        } catch (AddressNotFoundException e) {

            log.error(
                    "Address Not Found : {}",
                    e.getMessage(),
                    e
            );

            throw e;

        } catch (Exception e) {

            log.error(
                    "Unexpected error while creating transaction",
                    e
            );

            throw new RuntimeException(
                    "Unable to create transaction",
                    e
            );
        }
    }

    public User getUser(Long id){
        User user = userRepository.findByAccountId(id);
        return user;
    }

    // GET TRANSACTION BY ID
    public TransactionResponse getTransactionById(Long id) {

        log.info(
                "Fetching transaction with id: {}",
                id
        );

        try {

            Transaction transaction =
                    transactionRepository
                            .findById(id)
                            .orElseThrow(() ->
                                    new TransactionNotFound(
                                            "Transaction with id: "
                                                    + id
                                                    + " not found"
                                    )
                            );

            return mapToResponse(transaction);

        } catch (RuntimeException e) {

            log.error(
                    "Error while fetching transaction with id {}: {}",
                    id,
                    e.getMessage()
            );

            throw e;

        } catch (Exception e) {

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

    

   
    public List<TransactionResponse> getAllTransactions() {

        log.info("Fetching all transactions");

        try {

            return transactionRepository
                    .findAll()
                    .stream()
                    .map(this::mapToResponse)
                    .toList();

        } catch (Exception e) {

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

    
    private TransactionResponse mapToResponse(
            Transaction transaction) {

        return TransactionResponse.builder()
                .id(transaction.getId())

                .senderAccountId(
                        transaction
                                .getSenderAccount()
                                .getId()
                )

                .receiverAccountId(
                        transaction
                                .getReceiverAccount()
                                .getId()
                )

                .amount(transaction.getAmount())

                .status(transaction.getStatus())

                .createdAt(transaction.getCreatedAt())

                .build();
    }
}