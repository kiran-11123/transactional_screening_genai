package com.example.transaction_screening.service.user;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import com.example.transaction_screening.security.JwtService;
import com.example.transaction_screening.dto.user.LoginRequest;
import com.example.transaction_screening.dto.user.RegisterRequest;
import com.example.transaction_screening.dto.user.RegisterResponse;
import com.example.transaction_screening.entity.User;
import com.example.transaction_screening.entity.UserRole;
import com.example.transaction_screening.exception.user.UserAlreadyExistsException;
import com.example.transaction_screening.repository.UserRepository;
import com.example.transaction_screening.service.kafka.SendEmailService;
import java.util.UUID;
import com.example.transaction_screening.dto.kafka.EmailRequest;
import com.example.transaction_screening.dto.kafka.EmailResponse;
@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SendEmailService sendEmailService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService , SendEmailService sendEmailService){
         this.userRepository=userRepository;
         this.passwordEncoder=passwordEncoder;
            this.jwtService=jwtService;
            this.sendEmailService = sendEmailService;
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

                String idempotentKey = UUID.randomUUID().toString();

                EmailRequest emailReq = EmailRequest.builder().email(savedUser.getEmail()).IdempotentKey(idempotentKey).build();

                log.info("Sending the welcome email for : {} " , savedUser.getEmail());

                EmailResponse response = sendEmailService.sendEmail(emailReq);
                
                log.info("Welcome email response is : ", response);

          

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

    public String loginService(LoginRequest request){
           log.info("Login attempt for username: {}", request.getEmail());

    try {

        // 1. Find user
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + request.getEmail()
                        ));

        // 2. Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            log.warn(
                    "Invalid password for username: {}",
                    request.getEmail()
            );

            throw new BadCredentialsException(
                    "Invalid username or password"
            );
        }

     

        // 4. Generate JWT
        String token = jwtService.generateToken(user);

        log.info(
                "Login successful for username: {}",
                request.getEmail()
        );

        // 5. Return JWT
        return token;

    } catch (BadCredentialsException |
             UsernameNotFoundException e) {

        throw e;

    } catch (Exception e) {

        log.error(
                "Unexpected error while logging in user: {}",
                request.getEmail(),
                e
        );

        throw new RuntimeException(
                "Unable to login",
                e
        );
    }
    }


    }


