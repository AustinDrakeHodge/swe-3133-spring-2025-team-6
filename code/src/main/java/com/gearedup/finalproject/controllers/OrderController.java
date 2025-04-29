package com.gearedup.finalproject.controllers;

import com.gearedup.finalproject.entities.*;
import com.gearedup.finalproject.services.OrderService;
import com.gearedup.finalproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/checkout") // URLs stay like /checkout, /checkout/complete
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User)) {
            throw new RuntimeException("User not logged in");
        }

        org.springframework.security.core.userdetails.User userDetails =
            (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public String checkoutPage(Model model) {
        User user = getCurrentUser();
        model.addAttribute("user", user);
        return "checkout"; // resolves to templates/checkout.html
    }

    @PostMapping
    public String processCheckout(@RequestParam String address,
                                   @RequestParam String phone,
                                   @RequestParam String cardNumber,
                                   @RequestParam String expMonth,
                                   @RequestParam String expYear,
                                   @RequestParam String cvv,
                                   @RequestParam String shippingSpeed,
                                   Model model) {
        User user = getCurrentUser();

        if (cardNumber == null || cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            model.addAttribute("error", "Please enter a valid 16-digit credit card number.");
            return "checkout"; // back to  page if error
        }

        Order order = orderService.checkout(user, address, phone, cardNumber, expMonth, expYear, cvv, shippingSpeed);
        List<OrderItem> items = order.getItems();

        if (items == null || items.isEmpty()) {
            model.addAttribute("error", "Your cart is empty. Please add items before checking out.");
            return "checkout"; // back to checkout page if empty cart
        }

        BigDecimal subtotal = items.stream()
                                   .map(OrderItem::getPrice)
                                   .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.06));
        BigDecimal shippingCost;

        if ("Overnight".equalsIgnoreCase(shippingSpeed)) {
            shippingCost = new BigDecimal("29.00");
        } else if ("3-Day".equalsIgnoreCase(shippingSpeed)) {
            shippingCost = new BigDecimal("19.00");
        } else {
            shippingCost = new BigDecimal("0.00");
        }

        BigDecimal grandTotal = subtotal.add(tax).add(shippingCost);

        model.addAttribute("order", order);
        model.addAttribute("items", items);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("tax", tax);
        model.addAttribute("shipping", shippingCost);
        model.addAttribute("grandTotal", grandTotal);

        return "confirm_order"; // resolves to templates/confirm_order.html
    }

    @GetMapping("/complete")
    public String checkoutComplete(@RequestParam("orderId") Long orderId, Model model) {
        Order order = orderService.findOrderById(orderId);
        model.addAttribute("order", order);
        return "receipt"; // resolves to templates/receipt.html
    }

    @PostMapping("/complete")
    public String completeOrder(@RequestParam Long orderId, Model model) {
        User user = getCurrentUser();
        Order order = orderService.findOrderById(orderId);
        model.addAttribute("order", order);
        orderService.setCartItemsToSold(user);
        orderService.deleteCartItems(user);
        return "receipt"; // resolves to templates/receipt.html
    }

}
