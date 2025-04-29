package com.gearedup.finalproject.services;

import com.gearedup.finalproject.entities.*;
import com.gearedup.finalproject.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final PaymentInformationRepository paymentInformationRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public Order checkout(User user, String address, String phone, String cardNumber, String expMonth, String expYear, String cvv, String shippingSpeed) {
        // 1. Find the user's cart
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("No cart found for user"));

        // 2. Get cart items
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty.");
        }

        // 3. Create a PaymentInformation
        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setCreditCardNumber(cardNumber);
        paymentInformation.setPhoneNumber(phone);
        paymentInformation.setShippingAddress(address);

        BigDecimal shippingCost = switch (shippingSpeed) {
            case "Overnight" -> new BigDecimal("29.00");
            case "3-Day" -> new BigDecimal("19.00");
            default -> new BigDecimal("9.00");
        };
        paymentInformation.setShippingCost(shippingCost);
        paymentInformation.setShippingSpeed(shippingSpeed);
        paymentInformationRepository.save(paymentInformation);

        // 4. Create a new Order
        Order order = new Order();
        order.setUser(user);
        order.setPaymentInformation(paymentInformation);

        // 5. Convert CartItems to OrderItems
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setItem(cartItem.getItem());  // Assuming cartItem has a reference to InventoryItem
                    orderItem.setName(cartItem.getItem().getName());
                    orderItem.setPrice(cartItem.getItem().getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .toList();

        order.setItems(orderItems);

        // to mark items as sold:
//    for (CartItem cartItem : cartItems) {
//        InventoryItem item = cartItem.getItem();
//        item.setSold(true);
//        inventoryItemRepository.save(item);
//    }
        // Calculate subtotal
        BigDecimal subtotal = orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate tax (6%)
        BigDecimal tax = subtotal.multiply(new BigDecimal("0.06"));

        // Calculate grand total (subtotal + tax + shipping)
        BigDecimal grandTotal = subtotal.add(tax).add(shippingCost);

        // Set values to order
        order.setSubtotal(subtotal);
        order.setTax(tax);
        order.setGrandTotal(grandTotal);

        // Set last 4 digits of credit card
        String last4cc = cardNumber.length() >= 4 ? cardNumber.substring(cardNumber.length() - 4) : cardNumber;
        order.setLast4cc(last4cc);

        // Set order date
        order.setOrderDate(java.time.LocalDateTime.now());

        // Save the Order
        orderRepository.save(order);

        // Clear the cart (optional but usually recommended)
        //cartItemRepository.deleteAll(cartItems);

        return order;
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void setCartItemsToSold(User user) {
        // 1. Find the user's cart
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("No cart found for user"));

        // 2. Get cart items
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        for (CartItem cartItem : cartItems) {
            InventoryItem item = cartItem.getItem();
            item.setSold(true);
            inventoryItemRepository.save(item);
        }
    }

    public void deleteCartItems(User user) {
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("No cart found for user"));


        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(cartItems);
    }
}