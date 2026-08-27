package com.example.transaction_screening.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transaction_screening.entity.User;
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
     Optional<User> findByUsername(String username);

     boolean existsByUsername(String username);
     boolean existsByEmail(String email);
     User findByAccountId(Long id);

}
