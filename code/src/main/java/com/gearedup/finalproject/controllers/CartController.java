package com.gearedup.finalproject.controllers;

import com.gearedup.finalproject.entities.*;
import com.gearedup.finalproject.repositories.*;
import com.gearedup.finalproject.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository; // Correctly injected as a final field
    private final ShoppingCartRepository shoppingCartRepository;

    @GetMapping
    public String viewCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User)) {
            return "redirect:/login"; // Not logged in
        }

        org.springframework.security.core.userdetails.User userDetails =
            (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        String username = userDetails.getUsername();

        // Now lookup your REAL User entity from database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartService.listCartItems(user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);


        var cart = cartService.getOrCreateCart(user);
        model.addAttribute("subTotal", cartService.getCartTotal(cart));
        return "cart"; // templates/cart.html
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long itemId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User)) {
            return "redirect:/login"; // Not logged in
        }

        org.springframework.security.core.userdetails.User userDetails =
            (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        cartService.addItemToCart(user, itemId);
        return "redirect:/main";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return "redirect:/cart";
    }
}
