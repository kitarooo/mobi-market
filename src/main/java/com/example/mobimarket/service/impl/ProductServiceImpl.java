package com.example.mobimarket.service.impl;

import com.example.mobimarket.dto.request.ProductRequest;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.entity.Product;
import com.example.mobimarket.exception.NotFoundException;
import com.example.mobimarket.repository.ProductRepository;
import com.example.mobimarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

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
                    .price(product.getPrice())
                    .build());
        }

        return productResponses;
    }


    @Override
    public String addProduct(ProductRequest request) {
        /*if (productRepository.findByName(request.getName()).isPresent()) {
            throw new ProductAlreadyExistException("Такой продукт уже есть");
        }*/

        productRepository.save(mapToProduct(request));
        return "Продукт успешно добавлен!";
    }

    @Override
    public String updateProductById(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Продукт не найден!"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setFullDescription(request.getFullDescription());
        product.setShortDescription(request.getShortDescription());

        productRepository.save(product);

        return "Продукт успешно обновлен!";
    }

    @Override
    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Продукт не найден!"));
        productRepository.delete(product);

        return "Продукт был успешно удален!";
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return mapToResponse(productRepository.findAll());
    }

    @Override
    public ProductRequest getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Ппродукт не найден!"));

        return ProductRequest.builder()
                .name(product.getName())
                .fullDescription(product.getFullDescription())
                .price(product.getPrice())
                .shortDescription(product.getShortDescription())
                .build();
    }
}
