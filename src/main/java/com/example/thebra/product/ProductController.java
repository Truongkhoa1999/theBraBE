package com.example.thebra.product;

import com.example.thebra.orderitems.OrderItems;
import com.example.thebra.orderitems.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemsService orderItemsService;
    /////////

    @GetMapping("/")
    public List<Product> getAllProducts() {
        List<Product> products = productService.findProducts();
        return products;
    }

    @GetMapping("/orderItems/{id}")
    public List<String> getProductsByOrderId(@PathVariable UUID id) {
        List<OrderItems> listOfOrderItemsById = orderItemsService.findOrderItemsByOrderId(id);

        List<String> productImages = new ArrayList<>();

        for (OrderItems orderItem : listOfOrderItemsById) {
            UUID productId = orderItem.getProductId();

            Product product = productService.findProductById(productId);

            if (product != null && !product.getImages().isEmpty()) {
                productImages.add(product.getImages().get(0));
            }
        }

        return productImages;

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
    public void deleteProductById(@PathVariable UUID id) {
        productService.deleteProductById(id);
    }


}
