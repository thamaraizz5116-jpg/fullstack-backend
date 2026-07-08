package com.lastmile.backend.service;

import com.lastmile.backend.dto.DashboardDto;
import com.lastmile.backend.entity.DeliveryStatus;
import com.lastmile.backend.repository.DeliveryTaskRepository;
import com.lastmile.backend.repository.MerchantStoreRepository;
import com.lastmile.backend.repository.SystemUserRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final SystemUserRepository systemUserRepository;
    private final MerchantStoreRepository merchantStoreRepository;
    private final DeliveryTaskRepository deliveryTaskRepository;

    public DashboardService(SystemUserRepository systemUserRepository,
                             MerchantStoreRepository merchantStoreRepository,
                             DeliveryTaskRepository deliveryTaskRepository) {
        this.systemUserRepository = systemUserRepository;
        this.merchantStoreRepository = merchantStoreRepository;
        this.deliveryTaskRepository = deliveryTaskRepository;
    }

    public DashboardDto getDashboardSummary() {
        long totalUsers = systemUserRepository.count();
        long totalStores = merchantStoreRepository.count();
        long totalDeliveryTasks = deliveryTaskRepository.count();
        long completedDeliveries = deliveryTaskRepository.countByStatus(DeliveryStatus.DELIVERED);

        return new DashboardDto(totalUsers, totalStores, totalDeliveryTasks, completedDeliveries);
    }
}
