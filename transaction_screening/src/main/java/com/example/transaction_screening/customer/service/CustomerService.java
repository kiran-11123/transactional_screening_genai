package com.example.transaction_screening.customer.service;
import com.example.transaction_screening.customer.dto.CreateCustomerRequest;
import com.example.transaction_screening.customer.dto.CustomerResponse;
import org.springframework.stereotype.Service;
import com.example.transaction_screening.customer.entity.Customer;
import com.example.transaction_screening.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request){

        log.info("Creating customer with email: {}", request.getEmail());

        try{

            if(customerRepository.existsByEmail(request.getEmail())){
                 log.warn(
                    "Customer creation failed. Email already exists: {}",
                    request.getEmail()
                );

                throw new RuntimeException("Customer with Email" + request.getEmail() + "already exists") ;
            }
            
            Customer customer = new Customer();
            customer.setEmail(request.getEmail());
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setPhone(request.getPhone());
            
            Customer savedCustomer = customerRepository.save(customer);

            log.info("Customer with Email" + request.getEmail() + "has been saved successfully");

            CustomerResponse response = new CustomerResponse();
             response.setId(savedCustomer.getId());
            response.setFirstName(savedCustomer.getFirstName());
            response.setLastName(savedCustomer.getLastName());
            response.setEmail(savedCustomer.getEmail());
            response.setPhone(savedCustomer.getPhone());
            response.setCreatedAt(savedCustomer.getCreatedAt());


            return response;


        }
        catch(RuntimeException e){

             log.error(
                "Failed to create customer with email: {}. Error: {}",
                request.getEmail(),
                e.getMessage(),
                e
            );

            throw e;

        }
        catch(Exception e){
             log.error(
                "Unexpected error while creating customer with email: {}",
                request.getEmail(),
                e
            );

            throw new RuntimeException(
                "Unable to create customer. Please try again later.",
                e
            );
        }
           

    }

}
