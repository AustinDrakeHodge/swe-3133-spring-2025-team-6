package com.gearedup.finalproject.controllers;

import com.gearedup.finalproject.entities.ItemType;
import com.gearedup.finalproject.entities.User;
import com.gearedup.finalproject.repositories.UserRepository;
import com.gearedup.finalproject.services.AdminService;
import com.gearedup.finalproject.dtos.SaleDTO;
import com.gearedup.finalproject.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final InventoryService inventoryService;
    @Autowired
    private UserRepository userRepository;

    private void SetCurrentUser(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        org.springframework.security.core.userdetails.User userDetails =
            (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        // If you need full User entity (for userId, email, etc):
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (user != null) {
            model.addAttribute("user", user); // put user into model
        }
    }


    @GetMapping("/add_inventory")
    public String createItemForm(Model model) {
        List<ItemType> itemTypes = adminService.getAllItemTypes();
        model.addAttribute("itemTypes", itemTypes);

        SetCurrentUser(model);

        return "admin/add_inventory";
    }

    @PostMapping("/add_inventory")
    public String createItem(@RequestParam String name,
                              @RequestParam long itemTypeId,
                              @RequestParam String description,
                              @RequestParam double price,
                              @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        adminService.createInventoryItem(name, itemTypeId, description, price, imageFile);
        return "redirect:/main";
    }

    @GetMapping("/promote_user")
    public String promoteUserForm(Model model) {
        SetCurrentUser(model);

        List<User> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/promote_user";
    }

    @PostMapping("/promote_user")
    public String promoteUser(@RequestParam String username) {
        adminService.promoteUserToAdmin(username);
        return "redirect:/main";
    }

    @GetMapping("/view_sales")
    public String viewSales(Model model) {
        SetCurrentUser(model);

        List<SaleDTO> sales = adminService.getSalesReport();
        model.addAttribute("sales", sales);
        return "admin/view_sales";
    }

    @GetMapping("/sales/export")
    public void exportSalesToCsv(HttpServletResponse response) throws IOException {
        adminService.exportSalesReportToCsv(response);
    }

    @GetMapping("/receipt/{orderId}")
    public String viewReceipt(@PathVariable Long orderId, Model model) {
        var order = adminService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "receipt"; // Reuse your receipt.html
    }



    @GetMapping("/update/{itemId}")
    public String updateItem(@PathVariable Long itemId, Model model) {
        SetCurrentUser(model);
        var item = inventoryService.getInventoryItemByItemId(itemId);
        model.addAttribute("item", item);

        List<ItemType> itemTypes = adminService.getAllItemTypes();
        model.addAttribute("itemTypes", itemTypes);
        return "admin/edit_item";
    }

    @PostMapping("/update")
    public String updateItem(@RequestParam long id,
                                @RequestParam long itemTypeId,
                                @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam double price,
                              @RequestParam("imageFile") MultipartFile imageFile) throws IOException{
        adminService.updateInventoryItem(id, itemTypeId, name, description, price, imageFile);
        return "redirect:/main";
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestParam long id){
        adminService.deleteInventoryItem(id);
        return "redirect:/main";
    }


}