package org.example.project2.dto;

import lombok.*;
import org.example.project2.enums.CategoryQuality;

@Setter
@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor


public class ProductRequestDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String categoryQuality;
   // private Integer quantity;
    private String imageUrl;
    //private String currency;
}
