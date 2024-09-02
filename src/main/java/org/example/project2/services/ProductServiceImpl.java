package org.example.project2.services;

import org.example.project2.dto.ProductRequestDTO;
import org.example.project2.dto.ProductResponseDTO;
import org.example.project2.entities.Category;
import org.example.project2.entities.Product;
import org.example.project2.enums.CategoryQuality;
import org.example.project2.mappers.ProductMapper;
import org.example.project2.repositories.CategoryRepository;
import org.example.project2.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
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
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO, MultipartFile file) {
        Product product = productMapper.toProduct(productRequestDTO);

        CategoryQuality categoryQuality = CategoryQuality.valueOf(productRequestDTO.getCategoryQuality().toUpperCase());
        product.setCategoryQuality(categoryQuality);

        Category category = categoryRepository.findByCategoryQuality(categoryQuality);
        product.setCategory(category);

        // Handle file upload here and set the URL in the product entity
        String imageUrl = handleFileUpload(file);
        product.setImageUrl(imageUrl);

        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Product with ID " + id + " not found");
        }
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product with ID " + id, e);
        }
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());

        CategoryQuality categoryQuality = CategoryQuality.valueOf(productRequestDTO.getCategoryQuality().toUpperCase());
        product.setCategoryQuality(categoryQuality);

        Category category = categoryRepository.findByCategoryQuality(categoryQuality);
        product.setCategory(category);

        product.setImageUrl(productRequestDTO.getImageUrl());

        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductResponseDTO(updatedProduct);
    }

    private String handleFileUpload(MultipartFile file) {
        // Implement the logic to store the file and return the file URL
        // You might want to save the file to a server or cloud storage and then return the accessible URL.
        return "generated-image-url"; // Placeholder for the actual URL.
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }
}
