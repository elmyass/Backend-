package org.example.project2.repositories;

import org.example.project2.entities.Order1;
import org.example.project2.enums.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Order1Repository extends JpaRepository<Order1, Long> {
    List<Order1> findByOrderState(OrderState orderState);
    List<Order1> findByUserId(Long userId);
}
