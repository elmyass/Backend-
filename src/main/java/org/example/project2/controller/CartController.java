package org.example.project2.controller;

import lombok.AllArgsConstructor;
import org.example.project2.entities.Cart;
import org.example.project2.entities.Product;
import org.example.project2.exception.CartNotFoundException;
import org.example.project2.exception.InsufficientQuantityException;
import org.example.project2.exception.ProductNotFoundException;
import org.example.project2.security.SecurityUtils;
import org.example.project2.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/carts")
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add/{productId}")
    public Cart addProductToCart(@PathVariable Long productId, @RequestParam int quantity) {
        return cartService.addProductToCart(productId, quantity);
    }

    @DeleteMapping("/{productId}")
    public void deleteProductFromCart(@PathVariable Long productId) {
        cartService.deleteProductFromCart(productId);
    }

    @PutMapping("/{productId}")
    public Cart updateProductQuantity(@PathVariable Long productId, @RequestParam int quantity) {
        return cartService.updateProductQuantity(productId, quantity);
    }

    @GetMapping
    public List<Product> viewCart() {
        return cartService.viewCart();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<String> handleCartNotFoundException(CartNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientQuantityException.class)
    public ResponseEntity<String> handleInsufficientQuantityException(InsufficientQuantityException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
