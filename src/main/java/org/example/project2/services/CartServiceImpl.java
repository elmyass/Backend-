package org.example.project2.services;

import org.example.project2.entities.Cart;
import org.example.project2.entities.Product;
import org.example.project2.exception.CartNotFoundException;
import org.example.project2.exception.InsufficientQuantityException;
import org.example.project2.exception.ProductNotFoundException;
import org.example.project2.repositories.CartRepository;
import org.example.project2.repositories.ProductRepository;
import org.example.project2.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart addProductToCart(Long productId, int quantity) {
        String username = SecurityUtils.getUserIdFromPrincipal();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produit non trouvé"));

        int quantityToAdd = Math.min(product.getQuantity(), quantity);

        if (quantityToAdd == 0) {
            throw new InsufficientQuantityException("Quantité insuffisante pour le produit: " + product.getName());
        }

        Cart cart = cartRepository.findByUsername(username);
        if (cart == null) {
            cart = new Cart();
            cart.setUsername(username);
        }

        cart.getProducts().put(productId, cart.getProducts().getOrDefault(productId, 0) + quantityToAdd);
        product.setQuantity(product.getQuantity() - quantityToAdd);

        productRepository.save(product);
        cartRepository.save(cart);

        if (quantityToAdd < quantity) {
            throw new InsufficientQuantityException("Quantité insuffisante pour le produit: " + product.getName() + ". Seulement " + quantityToAdd + " ajouté(s) au panier.");
        }

        return cart;
    }

    @Override
    public void deleteProductFromCart(Long productId) {
        String username = SecurityUtils.getUserIdFromPrincipal();
        Cart cart = cartRepository.findByUsername(username);

        if (cart != null) {
            Integer currentQuantity = cart.getProducts().get(productId);
            if (currentQuantity != null) {
                cart.getProducts().remove(productId);

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException("Produit non trouvé"));

                product.setQuantity(product.getQuantity() + currentQuantity);

                productRepository.save(product);
                cartRepository.save(cart);
            } else {
                throw new ProductNotFoundException("Produit non trouvé dans le panier");
            }
        } else {
            throw new CartNotFoundException("Panier non trouvé pour l'utilisateur: " + username);
        }
    }

    @Override
    public Cart updateProductQuantity(Long productId, int newQuantity) {
        String username = SecurityUtils.getUserIdFromPrincipal();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produit non trouvé"));

        Cart cart = cartRepository.findByUsername(username);
        if (cart != null) {
            Integer currentQuantity = cart.getProducts().get(productId);

            if (currentQuantity != null) {
                int difference = newQuantity - currentQuantity;

                if (difference > 0) { // Augmentation de la quantité
                    int quantityToAdd = Math.min(product.getQuantity(), difference);
                    product.setQuantity(product.getQuantity() - quantityToAdd);
                    cart.getProducts().put(productId, currentQuantity + quantityToAdd);

                    if (quantityToAdd < difference) {
                        productRepository.save(product);
                        cartRepository.save(cart);
                        throw new InsufficientQuantityException("Quantité insuffisante pour le produit: " + product.getName() + ". Seulement " + quantityToAdd + " ajouté(s) au panier.");
                    }
                } else if (difference < 0) { // Diminution de la quantité
                    product.setQuantity(product.getQuantity() - difference); // Ajout au stock
                    cart.getProducts().put(productId, newQuantity);
                }

                productRepository.save(product);
                return cartRepository.save(cart);
            } else {
                throw new ProductNotFoundException("Produit non trouvé dans le panier");
            }
        } else {
            throw new CartNotFoundException("Panier non trouvé pour l'utilisateur: " + username);
        }
    }

    @Override
    public List<Product> viewCart() {
        String username = SecurityUtils.getUserIdFromPrincipal();
        Cart cart = cartRepository.findByUsername(username);
        if (cart != null) {
            return cart.getProducts().entrySet().stream()
                    .map(entry -> {
                        Product product = productRepository.findById(entry.getKey())
                                .orElseThrow(() -> new ProductNotFoundException("Produit avec ID " + entry.getKey() + " non trouvé."));
                        product.setQuantity(entry.getValue());
                        return product;
                    })
                    .collect(Collectors.toList());
        } else {
            throw new CartNotFoundException("Panier pour l'utilisateur " + username + " non trouvé.");
        }
    }
}
