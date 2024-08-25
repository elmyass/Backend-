package org.example.project2.repositories;

import org.example.project2.entities.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Cart findByUsername(String username);

}
