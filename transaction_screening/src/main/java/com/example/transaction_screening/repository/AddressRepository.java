package com.example.transaction_screening.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.transaction_screening.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{

}
