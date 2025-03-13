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
        if (userRepository.existsByUsername(user.getUsername())) { //проверяет существует ли пользователь по его юзернейму
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // шифрует пароль
        userRepository.save(user); // сохраняет пользователя в базе данных

        // добавляет роли для нового пользователя
        Authority authority = new Authority(user.getUsername(), "ROLE_USER");
        authorityRepository.save(authority); // сохраняет роль в базу данных

        userRepository.save(user); // сохраняет пользователя вместе с ролью

        return true; // конец
    }
}
