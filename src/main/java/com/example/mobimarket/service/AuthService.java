package com.example.mobimarket.service;

import com.example.mobimarket.dto.request.LoginRequest;
import com.example.mobimarket.dto.request.RegistrationRequest;
import com.example.mobimarket.dto.response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse login(LoginRequest request);
    String registration(RegistrationRequest request);
}
