package org.example.project2.services;

import org.example.project2.dto.ProductRequestDTO;
import org.example.project2.dto.ProductResponseDTO;
import org.example.project2.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAll();
    ProductResponseDTO findById(Long id);
    ProductResponseDTO save(ProductRequestDTO productRequestDTO, MultipartFile file);
    void deleteById(Long id);
    ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO);
    List<Product> searchProducts(String query);
}
