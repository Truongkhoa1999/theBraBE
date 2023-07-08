package com.example.thebra.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> findProducts() {
        return productRepository.findAll();
    }
    public Product findProductById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }
    public Product createNewProduct (Product product) {
        return productRepository.save(product);
    }
    public String deleteProductById (UUID id) {
        productRepository.deleteById(id);
        return ("your product " + id + " has been removed." );
    }
}
