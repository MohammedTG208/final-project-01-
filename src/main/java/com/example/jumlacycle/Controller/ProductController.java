package com.example.jumlacycle.Controller;

import com.example.jumlacycle.Model.Product;
import com.example.jumlacycle.Model.User;
import com.example.jumlacycle.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/product")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("get-all-products")
    public ResponseEntity allProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search-by/{productName}")
    public ResponseEntity searchByProductName(@PathVariable String productName) {
        return ResponseEntity.status(200).body(productService.SearchProductByName(productName));
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@Valid @RequestBody Product product) {
        productService.addNewProduct(product);
        return ResponseEntity.status(201).body("product added successfully");
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(201).body("product deleted successfully");
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity updateProduct(@PathVariable Integer productId, @Valid @RequestBody Product product) {
        productService.updateProduct(product, productId);
        return ResponseEntity.status(201).body("product updated successfully");
    }
}
