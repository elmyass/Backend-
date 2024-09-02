package org.example.project2.dto;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String categoryQuality;
    private Integer quantity;
    private String imageUrl;
    private String currency;
    private byte[] imageData;

    // Getters and Setters for all fields, including imageData


    public ProductResponseDTO(Long id, String name, String description, Double price, String categoryQuality,  Integer  quantity,  String imageUrl, String currency) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryQuality = categoryQuality;
        this.imageUrl = imageUrl;
        this.currency = currency;
        this.quantity = quantity;
    }
}

