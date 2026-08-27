package com.example.transaction_screening.dto.transaction;

import java.time.LocalDateTime;

import com.example.transaction_screening.entity.RiskLevel;
import com.example.transaction_screening.entity.WatchlistType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchlistResponse {
    private Long id;

    private String name;

    private String address;

    private String country;

    private RiskLevel riskLevel;

    private WatchlistType watchlistType;

    private boolean active;

    private LocalDateTime createdAt;

}
