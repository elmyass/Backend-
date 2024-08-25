package org.example.project2.controller;

import lombok.AllArgsConstructor;
import org.example.project2.dto.ProductRequestDTO;
import org.example.project2.dto.ProductResponseDTO;
import org.example.project2.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public ProductResponseDTO addProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.save(productRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.update(id, productRequestDTO);
    }
}
