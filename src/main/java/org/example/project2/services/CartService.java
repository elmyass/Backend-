package org.example.project2.services;

import org.example.project2.entities.Product;
import org.example.project2.entities.Cart;

import java.util.List;

public interface CartService {
    Cart addProductToCart( Long productId, int quantity);
    void deleteProductFromCart( Long productId );
    Cart updateProductQuantity( Long productId, int quantity);
    List<Product> viewCart( );

}
