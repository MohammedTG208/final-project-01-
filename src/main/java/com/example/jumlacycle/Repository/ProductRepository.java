package com.example.jumlacycle.Repository;

import com.example.jumlacycle.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductById(Integer id);
   List <Product> findProductByProductName(String productName);
}
