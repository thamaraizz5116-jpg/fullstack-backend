package com.lastmile.backend.repository;

import com.lastmile.backend.entity.DeliveryStatus;
import com.lastmile.backend.entity.DeliveryTask;
import com.lastmile.backend.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryTaskRepository extends JpaRepository<DeliveryTask, Long> {
    List<DeliveryTask> findByCourier(SystemUser courier);
    long countByStatus(DeliveryStatus status);
}
