package org.example.project2.dto;

import lombok.*;
import org.example.project2.enums.OrderState;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Order1RequestDTO {
    private OrderState orderState;
    private Long userId;
    private List<Long> orderDetailIds;
}
