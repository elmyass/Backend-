package org.example.project2.repositories;

import org.example.project2.entities.Category;
import org.example.project2.enums.CategoryQuality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByCategoryQuality(CategoryQuality categoryQuality);
}
