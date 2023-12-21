package com.example.mobimarket.service;

import com.example.mobimarket.dto.request.ProductRequest;
import com.example.mobimarket.dto.request.RefreshTokenRequest;
import com.example.mobimarket.dto.request.SendSmsRequest;
import com.example.mobimarket.dto.response.AuthenticationResponse;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.dto.request.UserRequest;
import com.example.mobimarket.dto.response.UserResponse;
import com.example.mobimarket.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    String updateProfileById(UserRequest request);
    String updateProfilePhoto(MultipartFile multipartFile, User user) throws IOException;
    List<ProductResponse> getAllMyProducts();
    List<ProductResponse> getAllMyLikedProducts();
    ProductRequest getProductById(Long id);
    String likeProduct(Long id);
    String numberConfirm(Integer code, SendSmsRequest request);
    String sendMessage(SendSmsRequest request);
    AuthenticationResponse refreshToken(RefreshTokenRequest request);
    UserResponse getUserById(Long id);
}
