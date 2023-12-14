package com.example.mobimarket.controller;

import com.example.mobimarket.dto.request.ProductRequest;
import com.example.mobimarket.dto.response.ProductResponse;
import com.example.mobimarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/addProduct")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String addProduct(@RequestBody ProductRequest request) {
        return productService.addProduct(request);
    }

    @PutMapping("/updateProduct/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String updateProductById( @PathVariable Long id,@RequestBody ProductRequest request) {
        return productService.updateProductById(id, request);
    }

    @DeleteMapping("/deleteProduct/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String deleteProductByID(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/allProducts")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getProduct/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ProductRequest getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
