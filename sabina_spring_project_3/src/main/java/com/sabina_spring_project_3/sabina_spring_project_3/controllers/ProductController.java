package com.sabina_spring_project_3.sabina_spring_project_3.controllers;

import com.sabina_spring_project_3.sabina_spring_project_3.models.Product;
import com.sabina_spring_project_3.sabina_spring_project_3.models.User;
import com.sabina_spring_project_3.sabina_spring_project_3.repositories.UserRepository;
import com.sabina_spring_project_3.sabina_spring_project_3.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/products")
    public String showProductsByCategory(@RequestParam("category") String category, Model model) {
        System.out.print(category);

        //List<Product> products = getProductsByCategory(category);
        // model.addAttribute("products", products);
        return "marketplace"; // убедись, что этот шаблон существует в папке templates
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

            return "redirect:/error";
        }


        newProduct.setUser(user);

        if (imageFile == null || imageFile.isEmpty()) {

            return "redirect:/add-product?error=image";
        }

        String result = newProduct.toString();
        System.out.println(result);
        productService.addProduct(newProduct, imageFile);


        return "redirect:/products/user/" + user.getId();
    }


    @PostMapping("/edit-product-process")
    public String editProduct(
            @ModelAttribute("editProduct") Product newProduct,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile imageFile) throws IOException {

        String username = userDetails.getUsername();
        System.out.println(username);

        newProduct.setLastUpdate(LocalDate.now());

        User user = userRepository.findByUsername(username);
        if (user == null) {

            return "redirect:/error";
        }

        newProduct.setUser(user);



        productService.addProduct(newProduct, imageFile);


        return "redirect:/products/user/" + user.getId();
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public Optional<Product> getProductDetails(@PathVariable Long id, Model model) {

        return productService.findById(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "redirect:/error";
        }

        productService.deleteProductById(id);


        return "redirect:/products/user/" + user.getId();
    }

    @GetMapping("/marketplace")
    public String getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            Model model) {
        List<Product> products;

        if (category == null || "All".equals(category)) {
            products = productService.getAllProducts(page, size);
        } else {
            products = productService.getProductsByCategory(category, page, size);
        }

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("hasMore", products.size() == size);
        model.addAttribute("category", category);
        return "marketplace";
    }


    @GetMapping("/more/{id}")
    @ResponseBody
    public Optional<Product> getProductMoreDetails(@PathVariable Long id, Model model) {

        return productService.findById(id);
    }







}