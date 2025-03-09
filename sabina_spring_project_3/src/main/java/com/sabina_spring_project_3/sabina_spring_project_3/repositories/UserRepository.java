package com.sabina_spring_project_3.sabina_spring_project_3.repositories;


import com.sabina_spring_project_3.sabina_spring_project_3.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
