package com.example.mobimarket.service.impl;

import com.example.mobimarket.dto.request.ProductRequest;
import com.example.mobimarket.dto.request.SendSmsRequest;
import com.example.mobimarket.dto.request.UserRequest;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.entity.Product;
import com.example.mobimarket.entity.User;
import com.example.mobimarket.enums.Status;
import com.example.mobimarket.exception.IncorrectTokenException;
import com.example.mobimarket.exception.NotFoundException;
import com.example.mobimarket.exception.TokenExpiredException;
import com.example.mobimarket.repository.ProductRepository;
import com.example.mobimarket.repository.UserRepository;
import com.example.mobimarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SmsSendService sendSmsService;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public String updateProfileById(Long id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Ошибка"));

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setBirthday(LocalDate.parse(request.getBirthday(), dateTimeFormatter));

        userRepository.save(user);
        return "Данные успешно обновлены!";
    }

    @Override
    public String updateProfilePhoto(String photo) {
        return null;
    }

    @Override
    public List<ProductResponse> getAllMyProducts() {
        User user = getAuthUser();
        // if INACTIVE - ?
        List<ProductResponse> productResponses = new ArrayList<>();

        for (Product product : user.getMyProducts()) {
            productResponses.add(ProductResponse.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .id(product.getId())
                    .build());
        }
        return productResponses;
    }

    @Override
    public List<ProductResponse> getAllMyLikedProducts() {
        User user = getAuthUser();

        List<ProductResponse> getAllMyLikedProducts = new ArrayList<>();

        for (Product product : user.getLikedProducts()) {
            getAllMyLikedProducts.add(ProductResponse.builder()
                    .id(product.getId())
                    .price(product.getPrice())
                    .name(product.getName())
                    .build());
        }


        return getAllMyLikedProducts;

    }

    @Override
    public ProductRequest getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Продукт не найден!"));

        return ProductRequest.builder()
                .name(product.getName())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .price(product.getPrice())
                .likes(product.getLikedUsers().size())
                .build();
    }

    @Override
    public String likeProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Продукт не найден!"));
        User user = getAuthUser();

        if (productRepository.findProductByIdAndFindUserById(user.getId(), product.getId()) > 0) {
            productRepository.dislikeProductByIdAndUserId(user.getId(), product.getId());
            return "Успешный дизлайк!";
        }

        user.getLikedProducts().add(product);
        productRepository.saveAll(user.getLikedProducts());
        userRepository.save(user);
        return "Лайк успешно поставлен!";
    }

    @Override
    public String numberConfirm(Integer code, SendSmsRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        if (!Objects.equals(user.getToken(), code)) {
            throw new IncorrectTokenException("Неверный код!");
        }

        if (user.getTokenExpiration().isBefore(LocalDateTime.now())) {
            user.setToken(0);
            user.setTokenExpiration(null);
            userRepository.save(user);
            throw new TokenExpiredException("Время использования вашего кода истекло! Пожалуйста запросите новый код!");
        }
        user.setToken(0);
        user.setTokenExpiration(null);
        user.setPhoneNumber(request.getPhoneNumber());
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        return "Вы успешно подтвердили номер телефона!";
    }

    @Override
    public String sendMessage(SendSmsRequest request) {
        Random random = new Random();
        Integer token = random.nextInt(1000, 9999);
        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        user.setToken(token);
        user.setTokenExpiration(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);
        return sendSmsService.sendSms(request, String.valueOf(token));
    }

    private User getAuthUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
