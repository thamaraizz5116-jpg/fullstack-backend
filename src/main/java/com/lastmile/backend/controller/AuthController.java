package com.lastmile.backend.controller;

import com.lastmile.backend.dto.JwtResponse;
import com.lastmile.backend.dto.LoginRequest;
import com.lastmile.backend.dto.RegisterRequest;
import com.lastmile.backend.dto.UserDto;
import com.lastmile.backend.entity.SystemUser;
import com.lastmile.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest request) {
        SystemUser registeredUser = authService.register(request);
        UserDto userDto = new UserDto(
                registeredUser.getId(),
                registeredUser.getUsername(),
                registeredUser.getEmail(),
                registeredUser.getRole()
        );
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse jwtResponse = authService.login(request);
        return ResponseEntity.ok(jwtResponse);
    }
}
