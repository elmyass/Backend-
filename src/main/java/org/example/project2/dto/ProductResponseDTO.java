package org.example.project2.dto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@Data
@NoArgsConstructor

public class ProductResponseDTO {
    private String name;
    private String price;
    private String categoryQuality;
    private Integer quantity;
}

