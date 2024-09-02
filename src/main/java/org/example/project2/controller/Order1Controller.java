package org.example.project2.controller;

import lombok.AllArgsConstructor;
import org.example.project2.dto.Order1RequestDTO;
import org.example.project2.dto.Order1ResponseDTO;
import org.example.project2.services.Order1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value =  "/orders")
@CrossOrigin("*")
public class Order1Controller {
    @Autowired
    private Order1Service order1Service;




    @GetMapping
    public List<Order1ResponseDTO> getAllOrders() {
        return order1Service.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Order1ResponseDTO getOrderById(@PathVariable Long id) {
        return order1Service.findById(id);
    }

    /*
        @GetMapping("/{id}")
        public ResponseEntity<Order1> getOrderById(@PathVariable Long id) {
            final var order = order1Service.findById(id);
            if(Objects.isNull(order)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(order , HttpStatus.NOT_FOUND);
        }
     */

    @PostMapping
    public Order1ResponseDTO addOrder(@RequestBody Order1RequestDTO order1RequestDTO) {
        return order1Service.save(order1RequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable Long id) {
        order1Service.deleteById(id);
    }

    @PutMapping("/{id}")
    public Order1ResponseDTO updateOrder(@PathVariable Long id, @RequestBody Order1RequestDTO order1RequestDTO) {
        //order1.setId(id);
        return order1Service.update(id, order1RequestDTO);
    }
}
