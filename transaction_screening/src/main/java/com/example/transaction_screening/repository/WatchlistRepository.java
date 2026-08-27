package com.example.transaction_screening.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.transaction_screening.entity.Watchlist;
import java.util.*;

public interface WatchlistRepository extends JpaRepository<Watchlist , Long > {

    List<Watchlist> findByActiveTrue();
      List<Watchlist> findByNameContainingIgnoreCaseAndActiveTrue(
            String name
    );
     List<Watchlist> findByCountryIgnoreCaseAndActiveTrue(
            String country
    );

}
