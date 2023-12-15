package com.example.mobimarket.service.impl;

import com.example.mobimarket.dto.request.SendSmsRequest;
import com.example.mobimarket.dto.request.UserRequest;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.entity.Product;
import com.example.mobimarket.entity.User;
import com.example.mobimarket.exception.NotFoundException;
import com.example.mobimarket.repository.ProductRepository;
import com.example.mobimarket.repository.UserRepository;
import com.example.mobimarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public String updateProfileById(Long id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Ошибка"));

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setBirthday(request.getBirthday());

        userRepository.save(user);

        return "Данные успешно обновлены!";
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
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Продукт не найден!"));
        User user = getAuthUser();

        if (user.getLikedProducts().contains(product)) {
            product.setLikes(product.getLikes() - 1);
            user.getLikedProducts().remove(product);
            productRepository.saveAll(user.getLikedProducts());
            return "Успешный дизлайк";
        }

        product.setLikes(product.getLikes() + 1);
        user.getLikedProducts().add(product);
        productRepository.saveAll(user.getLikedProducts());
        userRepository.save(user);

        return "Лайк успешно поставлен!";
    }

    @Override
    public String numberConfirm(Integer code, SendSmsRequest request) {
        return null;
    }

    @Override
    public String sendMessage(SendSmsRequest request) {
        return null;
    }
    private User getAuthUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
