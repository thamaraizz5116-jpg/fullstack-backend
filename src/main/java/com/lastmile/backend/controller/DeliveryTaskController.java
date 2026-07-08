package com.lastmile.backend.controller;

import com.lastmile.backend.dto.DeliveryStatusUpdateRequest;
import com.lastmile.backend.dto.DeliveryTaskDto;
import com.lastmile.backend.service.DeliveryTaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-tasks")
public class DeliveryTaskController {

    private final DeliveryTaskService deliveryTaskService;

    public DeliveryTaskController(DeliveryTaskService deliveryTaskService) {
        this.deliveryTaskService = deliveryTaskService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    public ResponseEntity<DeliveryTaskDto> createTask(@Valid @RequestBody DeliveryTaskDto dto) {
        DeliveryTaskDto savedTask = deliveryTaskService.createTask(dto);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    public ResponseEntity<List<DeliveryTaskDto>> getAllTasks() {
        return ResponseEntity.ok(deliveryTaskService.getAllTasks());
    }

    @GetMapping("/my-tasks")
    @PreAuthorize("hasRole('COURIER')")
    public ResponseEntity<List<DeliveryTaskDto>> getMyTasks(Authentication authentication) {
        return ResponseEntity.ok(deliveryTaskService.getTasksByCourier(authentication.getName()));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT', 'COURIER')")
    public ResponseEntity<DeliveryTaskDto> updateStatus(@PathVariable Long id,
                                                          @Valid @RequestBody DeliveryStatusUpdateRequest request) {
        DeliveryTaskDto updatedTask = deliveryTaskService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        deliveryTaskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
