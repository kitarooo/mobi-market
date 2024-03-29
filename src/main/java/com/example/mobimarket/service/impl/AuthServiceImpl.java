package com.example.mobimarket.service.impl;

import com.example.mobimarket.dto.request.LoginRequest;
import com.example.mobimarket.dto.request.RegistrationRequest;
import com.example.mobimarket.dto.response.AuthenticationResponse;
import com.example.mobimarket.entity.User;
import com.example.mobimarket.enums.Role;
import com.example.mobimarket.enums.Status;
import com.example.mobimarket.exception.BaseException;
import com.example.mobimarket.exception.NotFoundException;
import com.example.mobimarket.exception.UserAlreadyExistException;
import com.example.mobimarket.repository.UserRepository;
import com.example.mobimarket.security.jwt.JwtService;
import com.example.mobimarket.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {
    final UserRepository userRepository;
    final PasswordEncoder encoder;
    final PasswordEncoder passwordEncoder;
    final JwtService service;


    @Override
    public AuthenticationResponse login(LoginRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            var jwtToken = service.generateToken(user);
            var refreshToken = service.generateRefreshToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            throw new BaseException("Вы не зарегистрированы или неправильные данные!");
        }
    }

    @Override
    public String registration(RegistrationRequest request) {
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с email: " + request.getEmail() + " уже существует!");
        }

        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с username: " + request.getUsername() + " уже существует!");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .status(Status.INACTIVE)
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);

        return "Регистрация прошла успешно!";
    }
}
