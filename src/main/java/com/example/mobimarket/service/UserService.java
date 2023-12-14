package com.example.mobimarket.service;

import com.example.mobimarket.dto.request.SendSmsRequest;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.dto.request.UserRequest;

import java.util.List;

public interface UserService {
    String updateProfile(UserRequest request);
    String updateProfilePhoto(String photo);
    List<ProductResponse> getAllMyProducts();
    List<ProductResponse> getAllMyLikedProducts();
    ProductResponse getProductById(Long id);
    String likeProduct(Long id);
    String numberConfirm(Integer code, SendSmsRequest request);
    String sendMessage(SendSmsRequest request);
}
