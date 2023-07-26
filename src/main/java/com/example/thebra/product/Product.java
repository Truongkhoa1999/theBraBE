package com.example.thebra.product;


import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity(name = "product")
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    //    product info
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "Decimal(10,2)")
    private BigDecimal price;
    @Column(columnDefinition = "text")
    private String description;
    @Column(nullable = false)
    private String category;
    @ElementCollection
    @Column(nullable = false)
    private List<String> images;
    @ElementCollection
    @Column(nullable = false)
    private Map<String, Integer> productSize;


    public Product(
            String title,
            BigDecimal price,
            String description,
            String category,
            List<String> images,
            Map<String, Integer> productSize
    ) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.images = images;
        this.productSize = productSize;
    }

}
