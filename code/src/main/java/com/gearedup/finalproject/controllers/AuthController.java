package com.gearedup.finalproject.controllers;


import com.gearedup.finalproject.entities.*;
import com.gearedup.finalproject.services.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // templates/register.html
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String email,
                                Model model) {
        try {
            authService.register(username, password, email);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // templates/login.html
    }

 @PostMapping("/login")
public String loginUser(@RequestParam String username,
                         @RequestParam String password,
                         Model model, HttpSession session) {
    var userOpt = authService.login(username, password);
    if (userOpt.isPresent()) {
        // Create authentication token manually
        User user = userOpt.get();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
    .username(user.getUsername())
    .password(user.getPassword())
    .roles(user.isAdmin() ? "ADMIN" : "USER")
    .build();

UsernamePasswordAuthenticationToken auth =
    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/main";
    } else {
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
}

    @PostMapping("/promote")
    public String promoteUser(@RequestParam Long userId) {
        authService.promoteToAdmin(userId);
        return "redirect:/admin";
    }

}