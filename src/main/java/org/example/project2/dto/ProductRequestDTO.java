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
    private Double price;
    private String description;
    private CategoryQuality categoryQuality;

}
