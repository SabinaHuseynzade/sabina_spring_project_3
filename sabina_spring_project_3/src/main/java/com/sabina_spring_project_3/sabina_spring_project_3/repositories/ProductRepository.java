package com.sabina_spring_project_3.sabina_spring_project_3.repositories;

import com.sabina_spring_project_3.sabina_spring_project_3.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByUserId(Long userId);

}