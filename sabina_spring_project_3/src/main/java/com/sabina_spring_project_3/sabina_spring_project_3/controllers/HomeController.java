package com.sabina_spring_project_3.sabina_spring_project_3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/home", "/"})
    public String home(Model model) {
        model.addAttribute("message", "Welcome to home page!");
        return "home";
    }
}