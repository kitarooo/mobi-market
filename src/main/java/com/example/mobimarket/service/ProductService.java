package com.example.mobimarket.service;

import com.example.mobimarket.dto.request.ProductRequest;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.entity.Product;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequest request);
    String updateProductById(Long id, ProductRequest request);
    String deleteProduct(Long id);
    List<ProductResponse> getAllProducts();
    ProductRequest getProductById(Long id);

}
