package org.example.project2.services;

import org.example.project2.dto.ProductRequestDTO;
import org.example.project2.dto.ProductResponseDTO;
import org.example.project2.entities.Category;
import org.example.project2.entities.Product;
import org.example.project2.enums.CategoryQuality;
import org.example.project2.mappers.ProductMapper;
import org.example.project2.repositories.CategoryRepository;
import org.example.project2.repositories.ProductRepository;
import org.example.project2.storage.FileStorage;
import org.example.project2.storage.SystemFileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private SystemFileStorage systemFileStorage;

    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponseDTO)
                .collect(Collectors.toList());
    }

    private ProductResponseDTO mapToProductResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategoryQuality(product.getCategoryQuality().toString());
        dto.setImageUrl(product.getImageUrl());

        // Load the image data
        try {
            if (product.getImageUrl() != null) {
                dto.setImageData(systemFileStorage.getFile(product.getImageUrl()));
            }
        } catch (IOException e) {
            // Handle the exception (e.g., log it)
            dto.setImageData(null);
        }

        return dto;
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
        if (category == null) {
            throw new NoSuchElementException("No category found with quality: " + categoryQuality);
        }
        product.setCategory(category);

        String imageUrl = handleFileUpload(file);
        product.setImageUrl(imageUrl);

        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO, MultipartFile file) {
        Product product = productRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());

        CategoryQuality categoryQuality = CategoryQuality.valueOf(productRequestDTO.getCategoryQuality().toUpperCase());
        product.setCategoryQuality(categoryQuality);

        Category category = categoryRepository.findByCategoryQuality(categoryQuality);
        if (category == null) {
            throw new NoSuchElementException("No category found with quality: " + categoryQuality);
        }
        product.setCategory(category);

        if (file != null && !file.isEmpty()) {
            String imageUrl = handleFileUpload(file);
            product.setImageUrl(imageUrl);
        } else {
            product.setImageUrl(productRequestDTO.getImageUrl());
        }

        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductResponseDTO(updatedProduct);
    }

    @Override
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }

    private String handleFileUpload(MultipartFile file) {
        try {
            return systemFileStorage.saveFile(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}
