package com.lastmile.backend.service;

import com.lastmile.backend.dto.DeliveryTaskDto;
import com.lastmile.backend.entity.DeliveryStatus;
import com.lastmile.backend.entity.DeliveryTask;
import com.lastmile.backend.entity.MerchantStore;
import com.lastmile.backend.entity.SystemUser;
import com.lastmile.backend.exception.ResourceNotFoundException;
import com.lastmile.backend.repository.DeliveryTaskRepository;
import com.lastmile.backend.repository.MerchantStoreRepository;
import com.lastmile.backend.repository.SystemUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryTaskService {

    private final DeliveryTaskRepository deliveryTaskRepository;
    private final MerchantStoreRepository merchantStoreRepository;
    private final SystemUserRepository systemUserRepository;

    public DeliveryTaskService(DeliveryTaskRepository deliveryTaskRepository,
                                MerchantStoreRepository merchantStoreRepository,
                                SystemUserRepository systemUserRepository) {
        this.deliveryTaskRepository = deliveryTaskRepository;
        this.merchantStoreRepository = merchantStoreRepository;
        this.systemUserRepository = systemUserRepository;
    }

    public DeliveryTaskDto createTask(DeliveryTaskDto dto) {
        MerchantStore store = merchantStoreRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + dto.getStoreId()));

        DeliveryTask task = new DeliveryTask();
        task.setPickupAddress(dto.getPickupAddress());
        task.setDropAddress(dto.getDropAddress());
        task.setCustomerName(dto.getCustomerName());
        task.setCustomerPhone(dto.getCustomerPhone());
        task.setStatus(DeliveryStatus.PENDING);
        task.setStore(store);

        if (dto.getCourierId() != null) {
            SystemUser courier = systemUserRepository.findById(dto.getCourierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Courier not found with id: " + dto.getCourierId()));
            task.setCourier(courier);
        }

        DeliveryTask savedTask = deliveryTaskRepository.save(task);
        return mapToDto(savedTask);
    }

    public List<DeliveryTaskDto> getAllTasks() {
        return deliveryTaskRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<DeliveryTaskDto> getTasksByCourier(String courierUsername) {
        SystemUser courier = systemUserRepository.findByUsername(courierUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Courier not found"));

        return deliveryTaskRepository.findByCourier(courier)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public DeliveryTaskDto updateStatus(Long id, DeliveryStatus status) {
        DeliveryTask task = deliveryTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery task not found with id: " + id));

        task.setStatus(status);
        DeliveryTask updatedTask = deliveryTaskRepository.save(task);
        return mapToDto(updatedTask);
    }

    public void deleteTask(Long id) {
        DeliveryTask task = deliveryTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery task not found with id: " + id));
        deliveryTaskRepository.delete(task);
    }

    private DeliveryTaskDto mapToDto(DeliveryTask task) {
        DeliveryTaskDto dto = new DeliveryTaskDto();
        dto.setId(task.getId());
        dto.setPickupAddress(task.getPickupAddress());
        dto.setDropAddress(task.getDropAddress());
        dto.setCustomerName(task.getCustomerName());
        dto.setCustomerPhone(task.getCustomerPhone());
        dto.setStatus(task.getStatus());
        dto.setStoreId(task.getStore().getId());
        dto.setStoreName(task.getStore().getStoreName());

        if (task.getCourier() != null) {
            dto.setCourierId(task.getCourier().getId());
            dto.setCourierUsername(task.getCourier().getUsername());
        }

        return dto;
    }
}
