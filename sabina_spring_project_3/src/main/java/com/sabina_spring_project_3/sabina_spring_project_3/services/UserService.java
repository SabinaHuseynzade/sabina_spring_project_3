package com.sabina_spring_project_3.sabina_spring_project_3.services;

import com.sabina_spring_project_3.sabina_spring_project_3.models.Authority;
import com.sabina_spring_project_3.sabina_spring_project_3.models.User;
import com.sabina_spring_project_3.sabina_spring_project_3.repositories.AuthorityRepository;
import com.sabina_spring_project_3.sabina_spring_project_3.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false; // Пользователь уже существует
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифруем пароль
        userRepository.save(user); // Сохраняем пользователя в базе данных

        // Добавление роли (например, USER) для нового пользователя
        Authority authority = new Authority(user.getUsername(), "ROLE_USER");
        authorityRepository.save(authority); // Сохраните роль в базе данных

        // Связка пользователя с ролью
        userRepository.save(user); // Обновление пользователя с ролью

        return true; // Успешная регистрация
    }
}
