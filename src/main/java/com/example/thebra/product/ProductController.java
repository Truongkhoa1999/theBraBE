package com.example.thebra.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/")
    public List<Product> getAllProducts() {
        List<Product> products = productService.findProducts();
        return products;
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable UUID id) {
        Product product = productService.findProductById(id);
        return product;
    }
    @PostMapping("/")
    public Product createProduct(@RequestBody Product product) {
        return productService.createNewProduct(product);
    }
    @DeleteMapping("/{id}")
    public void deleteProductById (@PathVariable UUID id){
        productService.deleteProductById(id);
    }
}
