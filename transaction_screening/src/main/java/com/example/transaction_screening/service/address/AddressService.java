package com.example.transaction_screening.service.address;

import java.util.*;
import java.time.LocalDateTime;
import com.example.transaction_screening.entity.Address;
import org.springframework.stereotype.Service;
import com.example.transaction_screening.exception.user.UserNotFoundException;
import com.example.transaction_screening.dto.address.AddressRequest;
import com.example.transaction_screening.dto.address.AddressResponse;
import com.example.transaction_screening.repository.AddressRepository;
import com.example.transaction_screening.repository.UserRepository;
import com.example.transaction_screening.entity.User;
import lombok.extern.slf4j.Slf4j;
import com.example.transaction_screening.exception.address.AddressNotFoundException;
import com.example.transaction_screening.exception.address.AddressAlreadyExistsException;

@Service
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    public AddressService(AddressRepository addressRepository , UserRepository userRepository){
        this.addressRepository = addressRepository;
        this.userRepository=userRepository;
    }


    public AddressResponse addAddress(AddressRequest request, Long userId){

        try{


            User user = userRepository.findById(userId).orElseThrow(()->
            new UserNotFoundException(
                                    "User not found with id: " + userId
                            ));
            

                if (user.getAddress() != null) {

                throw new AddressAlreadyExistsException(
                        "User already has an address"
                );
            }

             Address address = Address.builder()
                    .houseNumber(request.getHouseNumber())
                    .street(request.getStreet())
                    .city(request.getCity())
                    .state(request.getState())
                    .postalCode(request.getPostalCode())
                    .country(request.getCountry())
                    .createdAt(LocalDateTime.now())
                    .build();

            user.setAddress(address);
            
            User savedUser = userRepository.save(user);

             Address savedAddress = savedUser.getAddress();
              log.info(
                    "Address created successfully for userId: {}",
                    userId
            );

            return AddressResponse.builder().city(savedAddress.getCity()).country(savedAddress.getCountry()).username(savedUser.getUsername()).state(savedAddress.getState()).street(savedAddress.getStreet()).houseNumber(savedAddress.getHouseNumber()).postalCode(savedAddress.getPostalCode()).build();
            



        }
        catch(UserNotFoundException e ){
              log.error(
                    "User not found: {}",
                    e.getMessage()
            );

            throw e;
        }
        catch(AddressAlreadyExistsException e ){
            log.warn(
                    "Address already exists for userId: {}",
                    userId
            );

            throw e;
        }
        catch(Exception e){
                log.error(
                    "Error while creating address for userId: {}",
                    userId,
                    e
            );

            throw new RuntimeException(
                    "Error while creating the address",
                    e
            );
        }
           
    }

    public AddressResponse getAddress(Long userId) {
        
        try{
            User user = findUser(userId);
        
        if (user.getAddress() == null) {
            throw new AddressNotFoundException("Address not found for user");
        }

        return mapToResponse(user);

    }
    catch(AddressNotFoundException e){
         log.info("Address Not found for user :{}" ,userId );
         throw e;
    }
    catch(Exception e){
         throw new RuntimeException("Something went wrong while fetching address");
    }
    }

    public AddressResponse updateAddress(AddressRequest request, Long userId) {
        User user = findUser(userId);
        Address address = user.getAddress();

        if (address == null) {
            throw new RuntimeException("Address not found for user");
        }

        address.setHouseNumber(request.getHouseNumber());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());

        return mapToResponse(userRepository.save(user));
    }

    public void deleteAddress(Long userId) {
        User user = findUser(userId);
        Address address = user.getAddress();

        if (address == null) {
            throw new RuntimeException("Address not found for user");
        }

        user.setAddress(null);
        userRepository.save(user);
        addressRepository.delete(address);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + userId));
    }

    private AddressResponse mapToResponse(User user) {
        Address address = user.getAddress();

        return AddressResponse.builder()
                .username(user.getUsername())
                .houseNumber(address.getHouseNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }

}



