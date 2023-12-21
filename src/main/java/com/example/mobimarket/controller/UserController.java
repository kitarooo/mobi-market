package com.example.mobimarket.controller;


import com.example.mobimarket.dto.request.RefreshTokenRequest;
import com.example.mobimarket.dto.request.SendSmsRequest;
import com.example.mobimarket.dto.request.UserRequest;
import com.example.mobimarket.dto.response.AuthenticationResponse;
import com.example.mobimarket.dto.response.ImageResponse;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.dto.response.UserResponse;
import com.example.mobimarket.entity.User;
import com.example.mobimarket.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
@RequiredArgsConstructor

public class UserController {
    private final UserServiceImpl userService;

    @PutMapping("/updateUserProfile")
    @Operation(summary = "Изменение своих личных данных", description = "Доступ всем пользователям!Ендпонит для изменения своих личных данных.")
    public String updateUserProfile(@RequestBody UserRequest request) {
        return userService.updateProfileById(request);
    }

    @PutMapping("/like")
    @Operation(summary = "Поставить лайк и добавить продукт в понравившееся или дизлайк(собственно удаление из понравившихся)", description = "Доступ пользователям, которые прошли полную регистрацию! Ендпоинт для добавления продукта в понравившееся, который создал другой пользователь.")
    public String like(@RequestParam Long id) {
        return userService.likeProduct(id);
    }

    @GetMapping("/allUsers")
    @Operation(summary = "Этот ендпоинт для теста, НЕ ПОДКЛЮЧАТЬ!")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/myProducts")
    @Operation(summary = "Мои продукты", description = "Доступ пользователям, которые прошли полную регистрацию! Ендпонит для отображения всех моих продуктов.")
    public List<ProductResponse> getMyProducts() {
        return userService.getAllMyProducts();
    }

    @GetMapping("/getAllMyLikedProducts")
    @Operation(summary = "Все продукты, которым поставил лайк и добавил в понравившееся", description = "Доступ всем пользователям, которые прошли полную регистрацию! Ендпонит для отображения всех продуктов, которых добавил в понравившееся.")
    public List<ProductResponse> getAllMyLikedProducts() {
        return userService.getAllMyLikedProducts();
    }

    @PostMapping("/send-sms")
    @Operation(summary = "Смс код на номер телефона", description = "Доступ всем пользователям! Отправлятся код на указанный пользователем номер телефона с кодом для подтверждения.")
    public String sendSmS(@RequestBody SendSmsRequest request) {
        return userService.sendMessage(request);
    }

    @PostMapping("/registerConfirm")
    @Operation(summary = "Для подтверждения полной регистрации", description = "Пользователь вводит цифры, которые были отправлены на номер телефона! Этот ендпоинт для сравнения кода котоорый указал пользователь и код который хранится в базе данных.")
    public String registerConfirm(@RequestParam Integer code, @RequestBody SendSmsRequest request) {
        return userService.numberConfirm(code, request);
    }

    @GetMapping("/refreshToken")
    @Operation(summary = "Refresh token", description = "Обновляет JWT токен если выданный JWT уже истек, действует 6 часов")
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return userService.refreshToken(request);
    }

    @PostMapping("/updatePhoto")
    public String updateProfilePhoto(@RequestParam MultipartFile multipartFile, @AuthenticationPrincipal User user) throws IOException {
        return userService.updateProfilePhoto(multipartFile, user);
    }

    @GetMapping("/getImage/{id}")
    public ImageResponse getImage(@PathVariable Long id) {
        return userService.getImageByUserId(id);
    }

    @GetMapping("/getUserById/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
