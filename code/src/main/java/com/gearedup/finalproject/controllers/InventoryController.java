package com.gearedup.finalproject.controllers;

import com.gearedup.finalproject.entities.*;
import com.gearedup.finalproject.services.*;
import com.gearedup.finalproject.repositories.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class InventoryController {
    private final InventoryService inventoryService;
    private final ItemTypeRepository itemTypeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemPictureRepository itemPictureRepository;

    @GetMapping()
    public String listInventory(Model model, HttpSession session) {
        List<InventoryItem> items = inventoryService.getAvailableItems();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User)) {
            return "redirect:/login"; // not logged in properly
    }

    org.springframework.security.core.userdetails.User userDetails =
        (org.springframework.security.core.userdetails.User) auth.getPrincipal();

    // If you need full User entity (for userId, email, etc):
    String username = userDetails.getUsername();
    User user = userRepository.findByUsername(username)
    .orElseThrow(() -> new RuntimeException("User not found"));
        if (user != null) {
            model.addAttribute("user", user); // put user into model
        }

    for (InventoryItem item : items) {
        List<ItemPicture> pictures = itemPictureRepository.findByInventoryItem(item);
        if (!pictures.isEmpty()) {
            item.setPictures(pictures);
        }
    }

    model.addAttribute("items", items);
    return "main";
}


    @GetMapping("/search")
    public String searchInventory(@RequestParam String keyword, Model model) {
        List<InventoryItem> items = inventoryService.searchItems(keyword);
        model.addAttribute("items", items);
        return "main"; // reuse main.html
    }

    @GetMapping("/add")
    public String showAddInventoryForm(Model model) {
        List<ItemType> types = itemTypeRepository.findAll();
        model.addAttribute("types", types);
        return "add_inventory"; // templates/add_inventory.html
    }

    @PostMapping("/add")
    public String addInventory(@RequestParam String name,
                                @RequestParam String description,
                                @RequestParam BigDecimal price,
                                @RequestParam Long itemTypeId,
                                @RequestParam List<String> imageUrls) {
        inventoryService.addInventoryItem(name, description, price, itemTypeId, imageUrls);
        return "redirect:/main";
    }
}
