package com.example.mobimarket.controller;

import com.example.mobimarket.dto.request.LoginRequest;
import com.example.mobimarket.dto.request.RegistrationRequest;
import com.example.mobimarket.dto.response.AuthenticationResponse;
import com.example.mobimarket.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/registration")
    public String registration(@RequestBody @Valid RegistrationRequest request) {
        return authService.registration(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}
