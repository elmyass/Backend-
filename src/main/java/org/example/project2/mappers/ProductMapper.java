
package org.example.project2.mappers;

import org.example.project2.dto.ProductRequestDTO;
import org.example.project2.dto.ProductResponseDTO;
import org.example.project2.entities.Category;
import org.example.project2.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

   @Mapping(source = "category.categoryQuality", target = "categoryQuality")
    ProductResponseDTO toProductResponseDTO(Product product);

    Product toProduct(ProductRequestDTO productRequestDTO);
}
