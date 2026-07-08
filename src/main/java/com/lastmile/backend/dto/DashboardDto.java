package com.lastmile.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardDto {
    private long totalUsers;
    private long totalStores;
    private long totalDeliveryTasks;
    private long completedDeliveries;
}
