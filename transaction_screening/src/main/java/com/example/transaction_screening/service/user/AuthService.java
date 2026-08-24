package com.example.transaction_screening.service.user;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;

import com.example.transaction_screening.dto.user.RegisterRequest;
import com.example.transaction_screening.dto.user.RegisterResponse;
import com.example.transaction_screening.entity.User;
import com.example.transaction_screening.entity.UserRole;
import com.example.transaction_screening.exception.user.UserAlreadyExistsException;
import com.example.transaction_screening.repository.UserRepository;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository , PasswordEncoder passwordEncoder){
         this.userRepository=userRepository;
         this.passwordEncoder=passwordEncoder;
    }


    public RegisterResponse registerService(RegisterRequest request){
         log.info(
                    "Registering new user with username: {}",
                    request.getUsername()
            );

            try{

                if (userRepository.existsByUsername(request.getUsername())) {

                throw new UserAlreadyExistsException(
                        "Username already exists"
                );
            }

            if (userRepository.existsByEmail(request.getEmail())) {

                throw new UserAlreadyExistsException(
                        "Email already exists"
                );
            }

  User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(
                            passwordEncoder.encode(
                                    request.getPassword()
                            )
                    )
                    .role(UserRole.USER)
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .build();

           User savedUser =  userRepository.save(user);

            log.info(
                    "User registered successfully: {}",
                    user.getUsername()
            );


            return RegisterResponse.builder().id(savedUser.getId()).email(savedUser.getEmail()).username(savedUser.getUsername()).build();


            }
            catch (UserAlreadyExistsException e) {

            log.error(
                    "User registration failed: {}",
                    e.getMessage()
            );

            throw e;

        } catch (Exception e) {

            log.error(
                    "Unexpected error while registering user",
                    e
            );

            throw new RuntimeException(
                    "Unable to register user",
                    e
            );
        }
    }


    }


