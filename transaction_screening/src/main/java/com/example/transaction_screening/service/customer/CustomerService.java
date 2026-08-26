package com.example.transaction_screening.service.customer;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.transaction_screening.entity.Customer;
import com.example.transaction_screening.exception.customer.CustomerAlreadyExistsException;
import com.example.transaction_screening.exception.customer.CustomerNotFoundException;
import com.example.transaction_screening.dto.Customer.CustomerRequest;
import com.example.transaction_screening.dto.Customer.CustomerResponse;
import com.example.transaction_screening.repository.CustomerRepository;
import com.example.transaction_screening.repository.UserRepository;
import com.example.transaction_screening.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
@Service
@Slf4j
public class CustomerService {
         
     
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerService (CustomerRepository customerRepository , UserRepository userRepository){
        this.customerRepository = customerRepository;
        this.userRepository=userRepository;
    }

    public CustomerResponse createCustomer(CustomerRequest request , Long userId){
                     log.info(
            "Creating customer for userId: {} with email: {}",
            userId,
            request.getEmail()
    );


         try{

             User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with id: " + userId
                        )
                );

                  if (user.getCustomer() != null) {

            throw new CustomerAlreadyExistsException(
                    "This user already has a customer"
            );
        }

            if(customerRepository.existsByEmail(request.getEmail())){
                  throw new CustomerAlreadyExistsException("Customer already exists with email "+request.getEmail());
            }

           Customer customer = Customer.builder().name(request.getName()).email(request.getEmail()).phone(request.getPhone()).createdAt(LocalDateTime.now()).user(user).build();
           Customer savedCustomer = customerRepository.save(customer);
           
            log.info("Customer created successfully with id: {}", savedCustomer.getId());

           CustomerResponse response = CustomerResponse.builder().name(savedCustomer.getName()).email(savedCustomer.getEmail()).createdAt(savedCustomer.getCreatedAt()).id(savedCustomer.getId()).build();

           return response;
        

         }
         catch(Exception e){
            log.error("Unexpected error while creating customer", e);
              
            throw new RuntimeException("Error while Creating the customer " , e);
         }
         
    }

    

    public CustomerResponse getCustomerById(Long id){
          
         log.info("Fetching customer with id: {}", id);
         try{
            Customer savedCustomer = customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer with id: {}  not found"+id));
            CustomerResponse response = CustomerResponse.builder().name(savedCustomer.getName()).email(savedCustomer.getEmail()).createdAt(savedCustomer.getCreatedAt()).build();
            
            return response;


         }
         catch(RuntimeException e){
               log.error(
                    "Error while fetching customer with id {}: {}",
                    id,
                    e.getMessage()
            );

            throw e;


         }

         catch(Exception e){
             log.error(
                    "Unexpected error while fetching customer with id {}",
                    id,
                    e
            );

            throw new RuntimeException(
                    "Unable to fetch customer",
                    e
            );
         }
    }


    public List<CustomerResponse> getAllCustomers(){
        try{
            
            log.info("Fetching all customers");
            return customerRepository.findAll().stream().map(this::mapToResponse).toList();
        }
        catch(Exception e){

            log.error("Error while fetching all customers", e);

            throw new RuntimeException(
                    "Unable to fetch customers",
                    e
            );
             
        }
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .id(customer.getId())
                .build();
    }

}
