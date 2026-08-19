package com.example.transaction_screening.account.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.transaction_screening.account.dto.AccountResponse;
import com.example.transaction_screening.account.dto.CreateAccountRequest;
import com.example.transaction_screening.account.entity.Account;
import com.example.transaction_screening.account.entity.AccountStatus;
import com.example.transaction_screening.account.repository.AccountRepository;
import com.example.transaction_screening.customer.entity.Customer;
import com.example.transaction_screening.customer.exception.ResourceAlreadyExistsException;
import com.example.transaction_screening.customer.exception.ResourceNotFoundException;
import com.example.transaction_screening.customer.repository.CustomerRepository;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository , CustomerRepository customerRepository){
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }
    
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request){

        log.info("Creating Account for customer ID : {}",request.getCustomerId());

        try{

            Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(()->{
                 log.warn(
                                "Customer not found. Customer ID: {}",
                                request.getCustomerId()
                        );


                         return new ResourceNotFoundException(
                                "Customer with ID "
                                        + request.getCustomerId()
                                        + " not found"
                        );
            });


            String accountNumber =generateAccountNumber();

            if(accountRepository.existsByAccountNumber(accountNumber)){
                   
                  log.error(
                        "Generated account number already exists: {}",
                        accountNumber
                );

                throw new ResourceAlreadyExistsException(
                        "Unable to generate unique account number"
                );
            }

            Account account = new Account();

            account.setAccountType(request.getAccountType());
            account.setAccountNumber(accountNumber);
            account.setCurrency(request.getCurrency());
             account.setBalance(BigDecimal.ZERO);
             account.setStatus(AccountStatus.ACTIVE);
             account.setCustomer(customer);


             Account savedAccount = accountRepository.save(account);

             log.info(
                    "Account created successfully. Account ID: {}, Account Number: {}",
                    savedAccount.getId(),
                    savedAccount.getAccountNumber()
            );



            return mapToResonse(savedAccount);

        }
        catch(ResourceNotFoundException | ResourceAlreadyExistsException e){

             log.warn(
                    "Account creation failed: {}",
                    e.getMessage()
            );

            throw e;
             
        }
        catch(Exception e){
              log.error(
                    "Unexpected error while creating account for customer ID: {}",
                    request.getCustomerId(),
                    e
            );

            throw new RuntimeException(
                    "Unable to create account. Please try again later.",
                    e
            );
        }
         
    }

    public String generateAccountNumber(){

        return "ACC-"+ UUID.randomUUID().toString().replace("-", "").substring(12).toUpperCase();
    }

    private AccountResponse mapToResonse(Account account){
           
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountStatus(account.getStatus());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        response.setCreatedAt(account.getCreatedAt());
        response.setCustomerId(account.getCustomer().getId());
        response.setCurrency(account.getCurrency());

        return response;
        
    }
 
   
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long id){
        log.info("Fetching account with ID: {}", id);

        try{

            Account account = accountRepository.findById(id).orElseThrow(()->{
                  log.warn(
                            "Account not found. Account ID: {}",
                            id
                    );

                    return new ResourceNotFoundException(
                            "Account with ID " + id + " not found"
                    );
            });
            log.info(
                "Account found successfully. Account ID: {}",
                id
        );

        return mapToResonse(account);

        }
        catch(ResourceNotFoundException e){

              log.warn(
                "Failed to fetch account: {}",
                e.getMessage()
        );

        throw e;
             
        }
        catch(Exception e){
             log.error(
                "Unexpected error while fetching account ID: {}",
                id,
                e
        );

        throw new RuntimeException(
                "Unable to fetch account. Please try again later.",
                e
        );
        }

    }

    @Transactional(readOnly = true)
    public AccountResponse getAccountByNumber(String accountNumber){

        log.info(
            "Fetching account with account number: {}",
            accountNumber
    );

    try{

         Account account = accountRepository
                .findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> {

                    log.warn(
                            "Account not found. Account number: {}",
                            accountNumber
                    );

                    return new ResourceNotFoundException(
                            "Account with number "
                                    + accountNumber
                                    + " not found"
                    );
                });

                return mapToResonse(account);


    }
    catch(ResourceNotFoundException e){
          log.warn(
                "Failed to fetch account: {}",
                e.getMessage()
        );

        throw e;
    }

    catch(Exception e){
           log.error(
                "Unexpected error while fetching account number: {}",
                accountNumber,
                e
        );

        throw new RuntimeException(
                "Unable to fetch account. Please try again later.",
                e
        );
    }
         
    }
    
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountByCustomerId(Long customerId){

        log.info(
            "Fetching accounts for customer ID: {}",
            customerId
    );

    try{

        if(!customerRepository.existsById(customerId)){
             log.warn(
                    "Customer not found. Customer ID: {}",
                    customerId
            );

            throw new ResourceNotFoundException(
                    "Customer with ID "
                            + customerId
                            + " not found"
            );
        }
        

        List<Account> accounts = accountRepository.findByCustomerId(customerId);

        log.info(
                "Found {} accounts for customer ID: {}",
                accounts.size(),
                customerId
        );

        return accounts.stream().map(this::mapToResonse).toList();
    }
    catch(ResourceNotFoundException e){
         
        log.warn("Failed to fetch customer accounts {}" ,e.getMessage());

        throw e;
    } 

    catch(Exception e){
         
         log.error(
                "Unexpected error while fetching accounts for customer ID: {}",
                customerId,
                e
        );

        throw new RuntimeException(
                "Unable to fetch customer accounts. Please try again later.",
                e
        );
    }

    } 





}
