package org.example.project2.services;

import org.example.project2.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    void deleteById(Long id);
    Category update(Category category, Long id);
    Category save(Category category);

}
