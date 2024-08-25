package org.example.project2.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.project2.enums.OrderState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private OrderState orderState;
    @OneToMany(mappedBy = "order1", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();
    @ManyToOne private User user;

}
