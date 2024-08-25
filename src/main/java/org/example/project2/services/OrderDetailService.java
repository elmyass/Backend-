package org.example.project2.services;

import org.example.project2.entities.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findAll();
    OrderDetail findById(Long id);
    OrderDetail save(OrderDetail orderDetail);
    void deleteById(Long id);
}
