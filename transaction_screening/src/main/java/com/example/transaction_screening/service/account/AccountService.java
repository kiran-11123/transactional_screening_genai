package com.example.transaction_screening.service.account;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.*;
import org.springframework.stereotype.Service;

import com.example.transaction_screening.dto.Account.AccountRequest;
import com.example.transaction_screening.dto.Account.AccountResponse;
import com.example.transaction_screening.entity.Account;
import com.example.transaction_screening.entity.Customer;
import com.example.transaction_screening.exception.account.AccountAlreadyExistsException;
import com.example.transaction_screening.exception.account.AccountNotFoundException;
import com.example.transaction_screening.exception.customer.CustomerNotFoundException;
import com.example.transaction_screening.repository.AccountRepository;
import com.example.transaction_screening.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {
     
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository , CustomerRepository customerRepository){
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }


    public AccountResponse createAccount(AccountRequest request){
         log.info(
                "Creating account with number: {}",
                request.getAccountNumber()
        );
        try{
               
            if(accountRepository.existsByAccountNumber(request.getAccountNumber())){
                 throw new AccountAlreadyExistsException(  "Account already exists with number: "
                                + request.getAccountNumber());

            }


            Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(()-> new CustomerNotFoundException(  "Customer with id: "
                                            + request.getCustomerId()
                                            + " not found"));
            
            Account account = Account.builder().accountNumber(request.getAccountNumber()).active(true).balance(request.getBalance()).createdAt(LocalDateTime.now()).currency(request.getCurrency()).customer(customer).build();

            Account savedAccount = accountRepository.save(account);

             log.info(
                    "Account created successfully with id: {}",
                    savedAccount.getId()
            );

            return mapToResponse(savedAccount);
            
        }
        catch(AccountAlreadyExistsException e){

             log.error(
                    "Account already exists: {}",
                    e.getMessage()
            );

            throw e;

        }
        catch(CustomerNotFoundException e){
             log.error(
                    "Customer not found while creating account: {}",
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

            Account account =
                    accountRepository.findById(id)
                            .orElseThrow(() ->
                                    new AccountNotFoundException(
                                            "Account with id: "
                                                    + id
                                                    + " not found"
                                    )
                            );

            return mapToResponse(account);

        } catch (AccountNotFoundException e) {

            log.error(
                    "Account not found: {}",
                    e.getMessage()
            );

            throw e;

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

    public List<AccountResponse> getAccountsByCustomerId(Long customerId){

           log.info(
                "Fetching accounts for customer id: {}",
                customerId
        );
        
        try{
             
            if(!customerRepository.existsById(customerId)){
               throw new CustomerNotFoundException(
                        "Customer with id: "
                                + customerId
                                + " not found"
                );
            }

            return accountRepository.findByCustomerId(customerId).stream().map(this :: mapToResponse).toList();
        }
        catch(CustomerNotFoundException e){
              log.error(
                    "Customer not found: {}",
                    e.getMessage()
            );

            throw e;

        }
        catch(Exception e){
             log.error(
                    "Unexpected error while fetching accounts for customer {}",
                    customerId,
                    e
            );

            throw new RuntimeException(
                    "Unable to fetch accounts",
                    e
            );
        }

    }
     



    public AccountResponse mapToResponse(Account account){

        return AccountResponse.builder().accountNumber(account.getAccountNumber()).active(account.isActive()).balance(account.getBalance()).createdAt(account.getCreatedAt()).currency(account.getCurrency()).customerId(account.getCustomer().getId()).id(account.getId()).build();


    }
}
