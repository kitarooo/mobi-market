package com.example.mobimarket.service;

import com.example.mobimarket.dto.request.ProductRequest;
import com.example.mobimarket.dto.request.RefreshTokenRequest;
import com.example.mobimarket.dto.request.SendSmsRequest;
import com.example.mobimarket.dto.response.AuthenticationResponse;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.dto.request.UserRequest;

import java.util.List;

public interface UserService {
    String updateProfileById(UserRequest request);
    String updateProfilePhoto(String photo);
    List<ProductResponse> getAllMyProducts();
    List<ProductResponse> getAllMyLikedProducts();
    ProductRequest getProductById(Long id);
    String likeProduct(Long id);
    String numberConfirm(Integer code, SendSmsRequest request);
    String sendMessage(SendSmsRequest request);
    AuthenticationResponse refreshToken(RefreshTokenRequest request);
}
