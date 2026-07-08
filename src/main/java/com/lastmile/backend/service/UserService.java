package com.lastmile.backend.service;

import com.lastmile.backend.dto.UserDto;
import com.lastmile.backend.entity.Role;
import com.lastmile.backend.entity.SystemUser;
import com.lastmile.backend.exception.ResourceNotFoundException;
import com.lastmile.backend.repository.SystemUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final SystemUserRepository systemUserRepository;

    public UserService(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

    public List<UserDto> getAllUsers() {
        return systemUserRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public UserDto updateUserRole(Long userId, Role role) {
        SystemUser user = systemUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.setRole(role);
        SystemUser updatedUser = systemUserRepository.save(user);

        return mapToDto(updatedUser);
    }

    private UserDto mapToDto(SystemUser user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}
