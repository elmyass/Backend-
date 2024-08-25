package org.example.project2.dto;

import lombok.*;
import org.example.project2.enums.OrderState;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Order1ResponseDTO {
    private Date createdAt;
    private OrderState orderState;
    private String username;
    private List<ProductResponseDTO> products;
}