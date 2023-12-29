package com.example.mobimarket.service.impl;

import com.cloudinary.Cloudinary;
import com.example.mobimarket.dto.request.ProductRequest;
import com.example.mobimarket.dto.request.RefreshTokenRequest;
import com.example.mobimarket.dto.request.SendSmsRequest;
import com.example.mobimarket.dto.request.UserRequest;
import com.example.mobimarket.dto.response.AuthenticationResponse;
import com.example.mobimarket.dto.response.ImageResponse;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.dto.response.UserResponse;
import com.example.mobimarket.entity.Product;
import com.example.mobimarket.entity.User;
import com.example.mobimarket.enums.Status;
import com.example.mobimarket.exception.*;
import com.example.mobimarket.repository.ProductRepository;
import com.example.mobimarket.repository.UserRepository;
import com.example.mobimarket.security.jwt.JwtService;
import com.example.mobimarket.service.FileUpload;
import com.example.mobimarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SmsSendService sendSmsService;
    private final JwtService jwtService;
    private final Cloudinary cloudinary;
    private final ImageUploadServiceImpl imageUploadService;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public String updateProfileById(UserRequest request) {
        User user = getAuthUser();

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setBirthday(LocalDate.parse(request.getBirthday(), dateTimeFormatter));

        userRepository.save(user);
        return "Данные успешно обновлены!";
    }

    @Override
    public String updateProfilePhoto(MultipartFile multipartFile, User user) throws IOException {
        user.setImageUrl(imageUploadService.saveImage(multipartFile));
        userRepository.save(user);
        return "Фото профиля успешно обновлен!!";
    }

    public ImageResponse getImageByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
        return ImageResponse.builder().imageUrl(user.getImageUrl()).build();
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
                    .likes(product.getLikedUsers().size())
                    .images(product.getImages())
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
                    .likes(product.getLikedUsers().size())
                            .images(product.getImages())
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
        if (user.getStatus() == Status.ACTIVE) {
            if (productRepository.findProductByIdAndFindUserById(user.getId(), product.getId()) > 0) {
                productRepository.dislikeProductByIdAndUserId(user.getId(), product.getId());
                return "Успешный дизлайк!";
            }

            user.getLikedProducts().add(product);
            productRepository.saveAll(user.getLikedProducts());
            userRepository.save(user);
            return "Лайк успешно поставлен!";
        } else {
            throw new UnauthorizedException("Для этой операции необходмио пройти полную регистрацию!");
        }
    }


    @Override
    public String numberConfirm(Integer code, SendSmsRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        if (!Objects.equals(user.getCode(), code)) {
            throw new IncorrectTokenException("Неверный код!");
        }

        if (user.getTokenExpiration().isBefore(LocalDateTime.now())) {
            user.setCode(0);
            user.setTokenExpiration(null);
            userRepository.save(user);
            throw new TokenExpiredException("Время использования вашего кода истекло! Пожалуйста запросите новый код!");
        }
        user.setCode(0);
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
        user.setCode(token);
        user.setTokenExpiration(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);
        return sendSmsService.sendSms(request, String.valueOf(token));
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("Такой пользователь не сущуествует!"));

        if (jwtService.isTokenValid(request.getToken(), user)) {
            return AuthenticationResponse.builder()
                    .refreshToken(jwtService.generateRefreshToken(user))
                    .accessToken(jwtService.generateToken(user))
                    .build();
        } else {
            throw new BaseException("Время токена истекло!");
        }
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

        return UserResponse.builder()
                .imageUrl(user.getImageUrl())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .birthday(user.getBirthday())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    private User getAuthUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
