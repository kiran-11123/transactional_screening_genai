package com.example.transaction_screening.service.account;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.*;
import org.springframework.stereotype.Service;

import com.example.transaction_screening.entity.User;
import com.example.transaction_screening.repository.UserRepository;
import com.example.transaction_screening.dto.Account.AccountRequest;
import com.example.transaction_screening.dto.Account.AccountResponse;
import com.example.transaction_screening.entity.Account;
import com.example.transaction_screening.exception.account.AccountAlreadyExistsException;
import com.example.transaction_screening.exception.account.AccountNotFoundException;
import com.example.transaction_screening.exception.user.UserNotFoundException;
import com.example.transaction_screening.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {
     
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    public AccountService(AccountRepository accountRepository , UserRepository userRepository ){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }


    public AccountResponse createAccount(AccountRequest request , Long userId){
         log.info(
                "Creating account with number: {}",
                request.getAccountNumber()
        );
        try{

                User user = findUser(userId);

                if(user.getAccount()!=null){
                         throw  new AccountAlreadyExistsException(  "Account already exists for user " + user.getUsername());


                }

               
            if(accountRepository.existsByAccountNumber(request.getAccountNumber())){
                 throw new AccountAlreadyExistsException(  "AccountNumber  already exists "
                                + request.getAccountNumber());

            }


            
            
            Account account = Account.builder().accountNumber(request.getAccountNumber()).active(true).balance(request.getBalance()).createdAt(LocalDateTime.now()).currency(request.getCurrency()).build();
        
            user.setAccount(account);
            User savedUserAccount = userRepository.save(user);
             log.info(
                    "Account created successfully for user ",
                    savedUserAccount.getUsername()
            );

            return mapToResponse(savedUserAccount.getAccount());
            
        }
        catch(AccountAlreadyExistsException e){

             log.error(
                    "Account already exists: {}",
                    e.getMessage()
            );

            throw e;

        }
        
        catch(Exception e){
             log.error(
                    "Unexpected error while creating account",
                    e
            );

            throw new RuntimeException(
                    "Error while creating account",
                    e
            );
        }
    }

    public AccountResponse getAccountById(Long id) {

        log.info(
                "Fetching account with id: {}",
                id
        );

        try {

              
           User user  = findUser(id);

           if(user.getAccount()==null){
                throw new AccountNotFoundException(
                     "Account not found for user id: " + id);
           }

            return mapToResponse(user.getAccount());

        } catch (Exception e) {

            log.error(
                    "Unexpected error while fetching account {}",
                    id,
                    e
            );

            throw new RuntimeException(
                    "Unable to fetch account",
                    e
            );
        }
    }

   private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + userId));
    }

     



    public AccountResponse mapToResponse(Account account){

        return AccountResponse.builder().accountNumber(account.getAccountNumber()).active(account.isActive()).balance(account.getBalance()).createdAt(account.getCreatedAt()).currency(account.getCurrency()).id(account.getId()).build();


    }
}
