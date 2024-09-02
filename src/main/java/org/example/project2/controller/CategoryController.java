package org.example.project2.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.entities.Category;
import org.example.project2.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        //category.setId(id);
        return categoryService.update(category, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryService.save(category);
    }
}
