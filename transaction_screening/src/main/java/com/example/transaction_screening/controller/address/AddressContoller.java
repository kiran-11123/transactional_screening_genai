package com.example.transaction_screening.controller.address;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.transaction_screening.dto.ApiResponse;
import com.example.transaction_screening.dto.address.AddressRequest;
import com.example.transaction_screening.dto.address.AddressResponse;
import com.example.transaction_screening.security.JwtPayloadDetails;
import com.example.transaction_screening.service.address.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/address")
public class AddressContoller {
      
    private final AddressService addressService;
    public AddressContoller(AddressService addressService){
        this.addressService =addressService;
    }

    @PostMapping
        public ResponseEntity<ApiResponse<AddressResponse>> addAddress(
            @Valid @RequestBody AddressRequest request,
            @AuthenticationPrincipal JwtPayloadDetails userDetails) {
         
        try{
            AddressResponse result = addressService.addAddress(
                request,
                userDetails.getId()
            );

            ApiResponse<AddressResponse> response =
                ApiResponse.<AddressResponse>builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Address created successfully")
                    .data(result)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch(Exception e){
            throw e;
        }
    }

        @GetMapping
        public ResponseEntity<ApiResponse<AddressResponse>> getAddress(
            @AuthenticationPrincipal JwtPayloadDetails userDetails) {
        AddressResponse result = addressService.getAddress(userDetails.getId());

        return ResponseEntity.ok(ApiResponse.<AddressResponse>builder()
            .status(HttpStatus.OK.value())
            .message("Address fetched successfully")
            .data(result)
            .build());
        }

        @PutMapping
        public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(
            @Valid @RequestBody AddressRequest request,
            @AuthenticationPrincipal JwtPayloadDetails userDetails) {
        AddressResponse result = addressService.updateAddress(
            request,
            userDetails.getId());

        return ResponseEntity.ok(ApiResponse.<AddressResponse>builder()
            .status(HttpStatus.OK.value())
            .message("Address updated successfully")
            .data(result)
            .build());
        }

        @DeleteMapping
        public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @AuthenticationPrincipal JwtPayloadDetails userDetails) {
        addressService.deleteAddress(userDetails.getId());

        return ResponseEntity.ok(ApiResponse.<Void>builder()
            .status(HttpStatus.OK.value())
            .message("Address deleted successfully")
            .build());
        }


}
