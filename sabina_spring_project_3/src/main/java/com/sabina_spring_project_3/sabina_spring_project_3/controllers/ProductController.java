package com.sabina_spring_project_3.sabina_spring_project_3.controllers;

import com.sabina_spring_project_3.sabina_spring_project_3.models.Product;
import com.sabina_spring_project_3.sabina_spring_project_3.models.User;
import com.sabina_spring_project_3.sabina_spring_project_3.repositories.UserRepository;
import com.sabina_spring_project_3.sabina_spring_project_3.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public String getProductsByUserId(@PathVariable Long userId, Model model) {
        List<Product> products = productService.getProductsByUserId(userId);
        model.addAttribute("products", products);
        model.addAttribute("newProduct", new Product());
        model.addAttribute("editProduct", new Product());
        model.addAttribute("totalSales", productService.getTotalSales());
        model.addAttribute("totalRevenue", productService.getTotalRevenue());
        return "products"; // Название HTML-шаблона для отображения
    }

    @PostMapping("/add-product-process")
    public String addProduct(
            @ModelAttribute("newProduct") Product newProduct,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        String username = userDetails.getUsername();
        System.out.println(username);

        newProduct.setLastUpdate(LocalDate.now());

        User user = userRepository.findByUsername(username);
        if (user == null) {
            // Обработка случая, когда пользователь не найден
            return "redirect:/error"; // Перенаправление на страницу ошибки или уведомление
        }

        // Установите пользователя для нового продукта
        newProduct.setUser(user); // Устанавливаем пользователя в Product

        // Проверка на null и не пустой файл
        if (imageFile == null || imageFile.isEmpty()) {
            // Обработка случая, когда файл не загружен
            return "redirect:/add-product?error=image"; // Перенаправление с ошибкой
        }

        String result = newProduct.toString();
        System.out.println(result);
        productService.addProduct(newProduct, imageFile);

        // Перенаправление на страницу с продуктами пользователя (предполагается, что у пользователя есть ID)
        return "redirect:/products/user/" + user.getId();
    }


    @PostMapping("/edit-product-process")
    public String editProduct(
            @ModelAttribute("editProduct") Product newProduct,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        String username = userDetails.getUsername();
        System.out.println(username);

        newProduct.setLastUpdate(LocalDate.now());

        User user = userRepository.findByUsername(username);
        if (user == null) {
            // Обработка случая, когда пользователь не найден
            return "redirect:/error"; // Перенаправление на страницу ошибки или уведомление
        }

        // Установите пользователя для нового продукта
        newProduct.setUser(user); // Устанавливаем пользователя в Product

        // Проверка на null и не пустой файл
        if (imageFile == null || imageFile.isEmpty()) {
            // Обработка случая, когда файл не загружен
            return "redirect:/add-product?error=image"; // Перенаправление с ошибкой
        }

        productService.addProduct(newProduct, imageFile);

        // Перенаправление на страницу с продуктами пользователя (предполагается, что у пользователя есть ID)
        return "redirect:/products/user/" + user.getId();
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public Optional<Product> getProductDetails(@PathVariable Long id, Model model) {
        // Загрузка товара по ID
        return productService.findById(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "redirect:/error"; // Обработка случая, когда пользователь не найден
        }

        productService.deleteProductById(id);

        // Перенаправление на страницу с продуктами пользователя после удаления
        return "redirect:/products/user/" + user.getId();
    }

    @GetMapping("/marketplace")
    public String getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        List<Product> products = productService.getAllProducts(page, size); // Получаем продукты
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("hasMore", products.size() == size); // Указываем, есть ли еще продукты
        return "marketplace"; // Название HTML-шаблона для отображения
    }

    @GetMapping("/more/{id}")
    @ResponseBody
    public Optional<Product> getProductMoreDetails(@PathVariable Long id, Model model) {
        // Загрузка товара по ID
        return productService.findById(id);
    }
}