package org.example.project2.services;

import org.example.project2.dto.ProductRequestDTO;
import org.example.project2.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAll();
    ProductResponseDTO findById(Long id);
    ProductResponseDTO save(ProductRequestDTO productRequestDTO);
    void deleteById(Long id);
    ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO);
}
