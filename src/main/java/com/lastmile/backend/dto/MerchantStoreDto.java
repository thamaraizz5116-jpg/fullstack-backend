package com.lastmile.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MerchantStoreDto {

    private Long id;

    @NotBlank(message = "Store name is required")
    private String storeName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    private Long ownerId;
    private String ownerUsername;
}
