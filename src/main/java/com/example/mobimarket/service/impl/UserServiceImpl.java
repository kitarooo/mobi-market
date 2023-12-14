package com.example.mobimarket.service.impl;

import com.example.mobimarket.dto.request.SendSmsRequest;
import com.example.mobimarket.dto.request.UserRequest;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.repository.ProductRepository;
import com.example.mobimarket.repository.UserRepository;
import com.example.mobimarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Override
    public String updateProfile(UserRequest request) {
        return null;
    }

    @Override
    public String updateProfilePhoto(String photo) {
        return null;
    }

    @Override
    public List<ProductResponse> getAllMyProducts() {
        return null;
    }

    @Override
    public List<ProductResponse> getAllMyLikedProducts() {
        return null;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return null;
    }

    @Override
    public String likeProduct(Long id) {
        return null;
    }

    @Override
    public String numberConfirm(Integer code, SendSmsRequest request) {
        return null;
    }

    @Override
    public String sendMessage(SendSmsRequest request) {
        return null;
    }
}
