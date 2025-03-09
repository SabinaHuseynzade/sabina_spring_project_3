package com.sabina_spring_project_3.sabina_spring_project_3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authorities") // Указывает имя таблицы
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String authority;

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
}
