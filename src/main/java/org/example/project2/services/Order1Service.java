package org.example.project2.services;

import org.example.project2.dto.Order1RequestDTO;
import org.example.project2.dto.Order1ResponseDTO;
import org.example.project2.enums.OrderState;

import java.util.List;

public interface Order1Service {
    List<Order1ResponseDTO> findAll();
    Order1ResponseDTO findById(Long id);
    Order1ResponseDTO save(Order1RequestDTO order1RequestDTO);
    void deleteById(Long id);
    Order1ResponseDTO update(Long id, Order1RequestDTO order1RequestDTO);
    List<Order1ResponseDTO> findByUserId(Long userId); // Nouvelle méthode
    List<Order1ResponseDTO> findByOrderState(OrderState orderState); // Nouvelle méthode
}
