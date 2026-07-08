package com.lastmile.backend.dto;

import com.lastmile.backend.entity.DeliveryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryTaskDto {

    private Long id;

    @NotBlank(message = "Pickup address is required")
    private String pickupAddress;

    @NotBlank(message = "Drop address is required")
    private String dropAddress;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Customer phone is required")
    private String customerPhone;

    private DeliveryStatus status;

    @NotNull(message = "Store id is required")
    private Long storeId;

    private String storeName;

    private Long courierId;
    private String courierUsername;
}
