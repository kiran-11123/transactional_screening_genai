package com.example.transaction_screening.controller.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction_screening.dto.ApiResponse;
import com.example.transaction_screening.dto.transaction.WatchlistRequest;
import com.example.transaction_screening.dto.transaction.WatchlistResponse;
import com.example.transaction_screening.service.transaction.WatchlistService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import java.util.*;

@RestController
@RequestMapping("/api/watchlist")
@Slf4j
public class WatchlistController {

    private final WatchlistService watchlistService;

    public WatchlistController(WatchlistService watchlistService){
        this.watchlistService =watchlistService;
    }

      @PostMapping
    public ResponseEntity<ApiResponse<WatchlistResponse>> create(
            @Valid @RequestBody WatchlistRequest request) {

        log.info(
                "Received request to create watchlist entry: {}",
                request.getName()
        );

        WatchlistResponse result =
                watchlistService.createWatchlist(request);

        ApiResponse<WatchlistResponse> response =
                ApiResponse.<WatchlistResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Watchlist entry created successfully")
                        .data(result)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

     @GetMapping
    public ResponseEntity<ApiResponse<List<WatchlistResponse>>> getAll() {

        List<WatchlistResponse> result =
                watchlistService.getAllActiveWatchlists();

        ApiResponse<List<WatchlistResponse>> response =
                ApiResponse.<List<WatchlistResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Watchlist fetched successfully")
                        .data(result)
                        .build();

        return ResponseEntity.ok(response);
    }

     @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WatchlistResponse>> getById(
            @PathVariable Long id) {

        WatchlistResponse result =
                watchlistService.getWatchlistById(id);

        ApiResponse<WatchlistResponse> response =
                ApiResponse.<WatchlistResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Watchlist fetched successfully")
                        .data(result)
                        .build();

        return ResponseEntity.ok(response);
    }

}
