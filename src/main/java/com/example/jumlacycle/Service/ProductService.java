package com.example.jumlacycle.Service;

import com.example.jumlacycle.API.ApiException;
import com.example.jumlacycle.Model.Customer;
import com.example.jumlacycle.Model.Facility;
import com.example.jumlacycle.Model.Product;
import com.example.jumlacycle.Model.User;
import com.example.jumlacycle.Repository.AuthRepository;
import com.example.jumlacycle.Repository.CustomerRepository;
import com.example.jumlacycle.Repository.FacilityRepository;
import com.example.jumlacycle.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final FacilityRepository facilityRepository;

    public List<Product> getAllProducts() {
            return productRepository.findAll();
    }

    public List<Product> SearchProductByName(String productName) {
        if (productRepository.findProductByProductName(productName).isEmpty()) {
            throw new ApiException("product not found");
        }else {
            return productRepository.findProductByProductName(productName);
        }
    }

    public void addNewProduct(Product product) {
        //..check how add this product facility or supplier to check the role if S or F to add in
        productRepository.save(product);
    }

    public void deleteProduct(Integer productId) {
        //..check how delete this product facility or supplier
        productRepository.deleteById(productId);
    }

    public void updateProduct(Product product,Integer productId) {
        //..check how update this product F or S
        Product oldProduct = productRepository.findProductById(productId);
        oldProduct.setProductName(product.getProductName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setQuantity(product.getQuantity());
        productRepository.save(oldProduct);
    }


}
