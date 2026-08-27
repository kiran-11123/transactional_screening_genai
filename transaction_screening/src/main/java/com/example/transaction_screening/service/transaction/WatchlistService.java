package com.example.transaction_screening.service.transaction;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.transaction_screening.dto.transaction.WatchlistRequest;
import com.example.transaction_screening.dto.transaction.WatchlistResponse;
import com.example.transaction_screening.entity.Watchlist;
import com.example.transaction_screening.repository.WatchlistRepository;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WatchlistService {
      
    private WatchlistRepository watchlistRepository;

    public WatchlistService(WatchlistRepository watchlistRepository){
        this.watchlistRepository = watchlistRepository;
    }

    public WatchlistResponse createWatchlist(WatchlistRequest request){

         log.info(
                "Creating watchlist entry for name: {}",
                request.getName()
        );

        try{

             Watchlist watchlist = Watchlist.builder()
                .name(request.getName())
                .address(request.getAddress())
                .country(request.getCountry())
                .riskLevel(request.getRiskLevel())
                .watchlistType(request.getWatchlistType())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

                Watchlist savedWatchlist =
                watchlistRepository.save(watchlist);

        log.info(
                "Watchlist created with id: {}",
                savedWatchlist.getId()
        );

        return mapToResponse(savedWatchlist);

        }
        catch(Exception e){
             throw new RuntimeException("Error while creating the watchlist " , e);
        }
         
    }

      public List<WatchlistResponse> getAllActiveWatchlists() {

        return watchlistRepository
                .findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public WatchlistResponse getWatchlistById(Long id) {

        Watchlist watchlist =
                watchlistRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Watchlist not found with id: " + id
                                )
                        );

        return mapToResponse(watchlist);
    }


    private WatchlistResponse mapToResponse(
            Watchlist watchlist) {

        return WatchlistResponse.builder()
                .id(watchlist.getId())
                .name(watchlist.getName())
                .address(watchlist.getAddress())
                .country(watchlist.getCountry())
                .riskLevel(watchlist.getRiskLevel())
                .watchlistType(watchlist.getWatchlistType())
                .active(watchlist.isActive())
                .createdAt(watchlist.getCreatedAt())
                .build();
    }
}
