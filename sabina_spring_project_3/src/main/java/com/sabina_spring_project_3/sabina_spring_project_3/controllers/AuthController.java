package com.sabina_spring_project_3.sabina_spring_project_3.controllers;

import com.sabina_spring_project_3.sabina_spring_project_3.models.LoginForm;
import com.sabina_spring_project_3.sabina_spring_project_3.models.User;
import com.sabina_spring_project_3.sabina_spring_project_3.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/login-process")
    public String loginUser(@ModelAttribute(name="loginForm") LoginForm loginForm, Model model) {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginForm.getUsername(), loginForm.getPassword()));
            return "redirect:/home";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Incorrect username or password.");
            return "login";
        }
    }

   @PostMapping("/register")
   public String registerUser(@ModelAttribute User user, Model model) {

       if (userService.registerUser(user)) {
            return "redirect:/login";
        } else {
           model.addAttribute("error", "User with that username already exist.");
        return "register";
       }
   }
}
