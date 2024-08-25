package org.example.project2.services;

import org.example.project2.dto.ProductRequestDTO;
import org.example.project2.dto.ProductResponseDTO;
import org.example.project2.entities.Category;
import org.example.project2.entities.Product;
import org.example.project2.mappers.ProductMapper;
import org.example.project2.repositories.CategoryRepository;
import org.example.project2.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponseDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toProduct(productRequestDTO);

        Category category = categoryRepository.findByCategoryQuality(productRequestDTO.getCategoryQuality());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toProduct(productRequestDTO);
        product.setId(id); // Ensure the ID is set for the update

        Category category = categoryRepository.findByCategoryQuality(productRequestDTO.getCategoryQuality());
        product.setCategory(category);
        
        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductResponseDTO(updatedProduct);
    }
}
