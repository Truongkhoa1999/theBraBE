package com.example.thebra.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.List;
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
    @Column(nullable = false, columnDefinition = "int")
    private int stock;
    @Column(columnDefinition = "text")
    private String description;
    @Column(nullable = false)
    private String category;
    @ElementCollection
    @Column(nullable = false)
    private List<String> images;

    public Product(
            String title,
            BigDecimal price,
            int stock,
            String description,
            String category,
            List<String> images
           ) {
        this.title = title;
        this.price = price;
        this.stock =stock;
        this.description = description;
        this.category = category;
        this.images = images;
    }

}
