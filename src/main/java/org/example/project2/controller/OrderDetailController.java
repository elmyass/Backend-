package org.example.project2.controller;

import org.example.project2.entities.OrderDetail;
import org.example.project2.entities.Product;
import org.example.project2.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/orderDetails")
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.findAll();
    }

    @GetMapping("/{id}")
    public OrderDetail getOrderDetailById(@PathVariable Long id) {
        return orderDetailService.findById(id);
    }

    @PostMapping
    public OrderDetail addOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.save(orderDetail);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderDetailById(@PathVariable Long id) {
        orderDetailService.deleteById(id);
    }

    @PutMapping("/{id}")
    public OrderDetail updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetail orderDetail) {
        //orderDetail.setId(id);
        orderDetail.setProduct(new Product());// Ensure the ID is set for updating
        return orderDetailService.save(orderDetail);
    }
}
