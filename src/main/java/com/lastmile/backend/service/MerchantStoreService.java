package com.lastmile.backend.service;

import com.lastmile.backend.dto.MerchantStoreDto;
import com.lastmile.backend.entity.MerchantStore;
import com.lastmile.backend.entity.SystemUser;
import com.lastmile.backend.exception.ResourceNotFoundException;
import com.lastmile.backend.repository.MerchantStoreRepository;
import com.lastmile.backend.repository.SystemUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantStoreService {

    private final MerchantStoreRepository merchantStoreRepository;
    private final SystemUserRepository systemUserRepository;

    public MerchantStoreService(MerchantStoreRepository merchantStoreRepository,
                                 SystemUserRepository systemUserRepository) {
        this.merchantStoreRepository = merchantStoreRepository;
        this.systemUserRepository = systemUserRepository;
    }

    public MerchantStoreDto addStore(MerchantStoreDto dto, String ownerUsername) {
        SystemUser owner = systemUserRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        MerchantStore store = new MerchantStore();
        store.setStoreName(dto.getStoreName());
        store.setAddress(dto.getAddress());
        store.setContactNumber(dto.getContactNumber());
        store.setOwner(owner);

        MerchantStore savedStore = merchantStoreRepository.save(store);
        return mapToDto(savedStore);
    }

    public List<MerchantStoreDto> getAllStores() {
        return merchantStoreRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public MerchantStoreDto getStoreById(Long id) {
        MerchantStore store = merchantStoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + id));
        return mapToDto(store);
    }

    private MerchantStoreDto mapToDto(MerchantStore store) {
        MerchantStoreDto dto = new MerchantStoreDto();
        dto.setId(store.getId());
        dto.setStoreName(store.getStoreName());
        dto.setAddress(store.getAddress());
        dto.setContactNumber(store.getContactNumber());
        dto.setOwnerId(store.getOwner().getId());
        dto.setOwnerUsername(store.getOwner().getUsername());
        return dto;
    }
}
