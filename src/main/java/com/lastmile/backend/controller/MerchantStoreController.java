package com.lastmile.backend.controller;

import com.lastmile.backend.dto.MerchantStoreDto;
import com.lastmile.backend.service.MerchantStoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class MerchantStoreController {

    private final MerchantStoreService merchantStoreService;

    public MerchantStoreController(MerchantStoreService merchantStoreService) {
        this.merchantStoreService = merchantStoreService;
    }

    @PostMapping
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<MerchantStoreDto> addStore(@Valid @RequestBody MerchantStoreDto dto,
                                                       Authentication authentication) {
        MerchantStoreDto savedStore = merchantStoreService.addStore(dto, authentication.getName());
        return new ResponseEntity<>(savedStore, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT', 'COURIER')")
    public ResponseEntity<List<MerchantStoreDto>> getAllStores() {
        return ResponseEntity.ok(merchantStoreService.getAllStores());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT', 'COURIER')")
    public ResponseEntity<MerchantStoreDto> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(merchantStoreService.getStoreById(id));
    }
}
