package org.example.project2.controller;

import lombok.AllArgsConstructor;
import org.example.project2.dto.ProductRequestDTO;
import org.example.project2.dto.ProductResponseDTO;
import org.example.project2.entities.Product;
import org.example.project2.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/products")
@CrossOrigin("*") // this annotation is used to allow out frontend to access the endpoints of this controller , we can specify origins using origins attribute

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


    @RequestMapping (value = "/upload",method = {RequestMethod.POST,RequestMethod.OPTIONS})
    public ProductResponseDTO addProduct(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("categoryQuality") String categoryQuality,
            @RequestParam("file") MultipartFile file
    ) {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO(id, name, description, price, categoryQuality, null);

        return productService.save(productRequestDTO, file);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.update(id, productRequestDTO);
    }
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("q") String query) {
        return productService.searchProducts(query);
    }
}
