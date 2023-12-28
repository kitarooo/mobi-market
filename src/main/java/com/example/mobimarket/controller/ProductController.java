package com.example.mobimarket.controller;

import com.example.mobimarket.dto.request.ProductRequest;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.dto.response.ProductResponseId;
import com.example.mobimarket.entity.Product;
import com.example.mobimarket.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/addProduct")
    @Operation(summary = "Добавление своего товара", description = "Доступ пользователям, которые прошли полную регистрацию! Ендпоинт для создания своего товара.")
    public ProductResponseId addProduct(@RequestBody ProductRequest request) {
        return productService.addProduct(request);
    }

    @PutMapping("/updateProduct/{id}")
    @Operation(summary = "Изменить данные  о продукте", description = "Доступ пользователям, которые прошли полную регистрацию! Ендпонит для изменения данных продукта.")
    public String updateProductById( @PathVariable Long id,@RequestBody ProductRequest request) {
        return productService.updateProductById(id, request);
    }

    @DeleteMapping("/deleteProduct/{id}")
    @Operation(summary = "Удаление своего продукта и удаление из бд", description = "Доступ пользователям, которые прошли полную регистрацию! Ендпонит для удаления продукта.")
    public String deleteProductByID(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/allProducts")
    @Operation(summary = "Все продукты", description = "Доступ всем пользователям! Ендпонит для отображения всех продуктов.")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getProduct/{id}")
    @Operation(summary = "Получение продукта по id", description = "Доступ всем пользователям! Ендпонит для отображения одного продукта с полным описанием продукта.")
    public ProductRequest getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/updateProductImages/{id}")
    @Operation(summary = "Добавление фотографии для продукта", description = "Доступ всем пользователям, которые зарегистрированы полностью. Ендпоинт для добавления фотографии продукта, находит продукт по id, т.к. названия продуктов могут составлять коллизию и это нормально!")
    public String updateProductImages(@PathVariable Long id, @RequestParam MultipartFile[] multipartFiles) {
        return productService.updateProductImages(id, multipartFiles);
    }
}
