package com.example.mobimarket.service;

import com.example.mobimarket.dto.request.ProductRequest;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.dto.response.ProductResponseId;
import com.example.mobimarket.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductResponseId addProduct(ProductRequest request);
    String updateProductById(Long id, ProductRequest request);
    String deleteProduct(Long id);
    List<ProductResponse> getAllProducts();
    ProductRequest getProductById(Long id);

    String updateProductImages(Long id, MultipartFile[] multipartFiles);
}
