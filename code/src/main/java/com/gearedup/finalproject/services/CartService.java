package com.gearedup.finalproject.services;

import com.gearedup.finalproject.entities.*;
import com.gearedup.finalproject.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public ShoppingCart getOrCreateCart(User user) {
        return shoppingCartRepository.findByUser(user)
                .orElseGet(() -> shoppingCartRepository.save(ShoppingCart.builder().user(user).build()));
    }

    public void addItemToCart(User user, Long itemId) {
        InventoryItem item = inventoryItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        if (item.isSold()) {
            throw new RuntimeException("Item already sold");
        }
        ShoppingCart cart = getOrCreateCart(user);
        CartItem cartItem = CartItem.builder().cart(cart).item(item).build();
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> listCartItems(User user) {
        ShoppingCart cart = getOrCreateCart(user);
        return cartItemRepository.findByCart(cart);
    }

    public void removeItemFromCart(Long id) {
        cartItemRepository.deleteById(id);
    }

    public void clearCart(ShoppingCart cart) {
        List<CartItem> items = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(items);
    }

    public BigDecimal getCartTotal(ShoppingCart cart) {
        List<CartItem> items = cartItemRepository.findByCart(cart);

        var subtotal = BigDecimal.ZERO;
        for (CartItem item : items) {
            subtotal = subtotal.add(item.getItem().getPrice());
        }

        return subtotal;
    }
}
