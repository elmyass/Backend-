package org.example.project2.mappers;

import org.example.project2.dto.Order1ResponseDTO;
import org.example.project2.dto.ProductResponseDTO;
import org.example.project2.entities.Order1;
import org.example.project2.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface Order1Mapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(target = "products", expression = "java(order.getOrderDetails().stream().map(orderDetail -> toProductResponseDTO(orderDetail.getProduct())).collect(java.util.stream.Collectors.toList()))")
    Order1ResponseDTO toOrderResponseDTO(Order1 order);

    default ProductResponseDTO toProductResponseDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getCategoryQuality().toString(),
                product.getQuantity(),
                product.getImageUrl(),
                product.getCurrency()
        );
    }
}
