package com.sabina_spring_project_3.sabina_spring_project_3.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FileController {
    private final ResourceLoader resourceLoader;

    public FileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/uploads/{filename:.+}")//для загрузки файлов
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            //получает имя файла и заугружает его с указанного пути
            Resource resource = resourceLoader.getResource("file:C:/Users/ASUS/Documents/GitHub/sabina_spring_project_3/sabina_spring_project_3/uploads/" + filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}