package com.example.mobimarket.service.impl;

import com.example.mobimarket.dto.request.ProductRequest;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.dto.response.ProductResponseId;
import com.example.mobimarket.entity.Image;
import com.example.mobimarket.entity.Product;
import com.example.mobimarket.entity.User;
import com.example.mobimarket.enums.Status;
import com.example.mobimarket.exception.NotFoundException;
import com.example.mobimarket.exception.UnauthorizedException;
import com.example.mobimarket.repository.ImageRepository;
import com.example.mobimarket.repository.ProductRepository;
import com.example.mobimarket.repository.UserRepository;
import com.example.mobimarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ImageUploadServiceImpl uploadService;
    private final ImageRepository imageRepository;

    public Product mapToProduct(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .fullDescription(request.getFullDescription())
                .shortDescription(request.getShortDescription())
                .price(request.getPrice())
                .build();
    }

    public List<ProductResponse> mapToResponse(List<Product> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            productResponses.add(ProductResponse.builder()
                    .name(product.getName())
                    .id(product.getId())
                    .likes(product.getLikedUsers().size())
                    .price(product.getPrice())
                    .images(product.getImages())
                    .build());
        }

        return productResponses;
    }


    @Override
    public ProductResponseId addProduct(ProductRequest request) {
        User user = getAuthUser();
        if (user.getStatus() == Status.ACTIVE) {
            Product product = productRepository.save(mapToProduct(request));
            product.setUser(user);
            user.getMyProducts().add(product);
            System.out.println(user.getMyProducts());
            userRepository.save(user);
            productRepository.save(product);

            return ProductResponseId.builder().id(product.getId()).build();
        } else {
            throw new UnauthorizedException("Для добавления продукта - нужно пройти полную регистрацию");
        }
    }



    @Override
    public String updateProductById(Long id, ProductRequest request) {
        User user = getAuthUser();
        if (user.getStatus() == Status.ACTIVE) {
            Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Продукт не найден!"));

            product.setName(request.getName());
            product.setPrice(request.getPrice());
            product.setFullDescription(request.getFullDescription());
            product.setShortDescription(request.getShortDescription());

            productRepository.save(product);

            return "Продукт успешно обновлен!";
        } else {
            throw new UnauthorizedException("Для изменения продукта - нужно пройти полную регистрацию!");
        }
    }

    @Override
    public String deleteProduct(Long id) {
        User user = getAuthUser();
        if (user.getStatus() == Status.ACTIVE) {
            Product product = productRepository.findByIdAndIdOfUser(id, user.getId()).orElseThrow(() -> new NotFoundException("Продукт не найден!"));
            productRepository.deleteProductByUserIdAndProductId(user.getId(), product.getId());
            productRepository.deleteProductFromUsersLikeProduct(id);
            productRepository.deleteProductById(id);
            return "Продукт был успешно удален!";
        } else {
            throw new UnauthorizedException("Для удаления продукта - нужно пройти полную регистрацию!");
        }
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return mapToResponse(productRepository.findAll());
    }

    @Override
    public ProductRequest getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Продукт не найден!"));

        return ProductRequest.builder()
                .name(product.getName())
                .fullDescription(product.getFullDescription())
                .price(product.getPrice())
                .likes(product.getLikedUsers().size())
                .shortDescription(product.getShortDescription())
                .images(product.getImages())
                .build();
    }

    @Override
    public String updateProductImages(Long id, MultipartFile[] multipartFiles) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Продукт не найден!"));
        List<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            images.add(Image.builder().imageUrl(uploadService.saveImage(multipartFile)).build());
        }

        //product.setImages(images);

        for (Image image : images) {
            product.getImages().add(image);
            image.setProduct(product);
        }
        imageRepository.saveAll(images);
        productRepository.save(product);
        return "Данные успешно обновлены!";
    }

    private User getAuthUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
