package org.example.project2.dto;

import lombok.*;

@AllArgsConstructor
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
}

