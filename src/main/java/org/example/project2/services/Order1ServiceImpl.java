package org.example.project2.services;

import org.example.project2.dto.Order1RequestDTO;
import org.example.project2.dto.Order1ResponseDTO;
import org.example.project2.entities.Order1;
import org.example.project2.entities.OrderDetail;
import org.example.project2.entities.User;
import org.example.project2.enums.OrderState;
import org.example.project2.mappers.Order1Mapper;
import org.example.project2.repositories.Order1Repository;
import org.example.project2.repositories.OrderDetailRepository;
import org.example.project2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class Order1ServiceImpl implements Order1Service {

    @Autowired
    private Order1Repository order1Repository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Order1Mapper order1Mapper;

    @Override
    public List<Order1ResponseDTO> findAll() {
        return order1Repository.findAll()
                .stream()
                .map(order1Mapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Order1ResponseDTO findById(Long id) {
        Order1 order = order1Repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return order1Mapper.toOrderResponseDTO(order);
    }

    @Override
    public Order1ResponseDTO save(Order1RequestDTO order1RequestDTO) {
        User user = userRepository.findById(order1RequestDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        List<OrderDetail> orderDetails = order1RequestDTO.getOrderDetailIds()
                .stream()
                .map(orderDetailRepository::findById)
                .map(opt -> opt.orElseThrow(EntityNotFoundException::new))
                .collect(Collectors.toList());

        Order1 order = new Order1();
        order.setUser(user);
        order.setOrderDetails(orderDetails);
        order.setOrderState(order1RequestDTO.getOrderState());
        order.setCreatedAt(new Date());

        Order1 savedOrder = order1Repository.save(order);
        return order1Mapper.toOrderResponseDTO(savedOrder);
    }

    @Override
    public void deleteById(Long id) {
        order1Repository.deleteById(id);
    }

    @Override
    public Order1ResponseDTO update(Long id, Order1RequestDTO order1RequestDTO) {
        Order1 order = order1Repository.findById(id).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(order1RequestDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        List<OrderDetail> orderDetails = order1RequestDTO.getOrderDetailIds()
                .stream()
                .map(orderDetailRepository::findById)
                .map(opt -> opt.orElseThrow(EntityNotFoundException::new))
                .collect(Collectors.toList());

        order.setUser(user);
        order.setOrderDetails(orderDetails);
        order.setOrderState(order1RequestDTO.getOrderState());

        Order1 updatedOrder = order1Repository.save(order);
        return order1Mapper.toOrderResponseDTO(updatedOrder);
    }

    @Override
    public List<Order1ResponseDTO> findByUserId(Long userId) {
        return order1Repository.findByUserId(userId)
                .stream()
                .map(order1Mapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order1ResponseDTO> findByOrderState(OrderState orderState) {
        return order1Repository.findByOrderState(orderState)
                .stream()
                .map(order1Mapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }
}
