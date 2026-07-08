package com.lastmile.backend.dto;

import com.lastmile.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Long userId;
    private String username;
    private Role role;
}
