package com.gearedup.finalproject.services;

import com.gearedup.finalproject.dtos.SaleDTO;
import com.gearedup.finalproject.entities.*;
import com.gearedup.finalproject.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final OrderRepository orderRepository;
    private final ItemPictureRepository itemPictureRepository;
    private final ItemTypeRepository itemTypeRepository;

    public void createInventoryItem(String name, long itemTypeId, String description, double price, MultipartFile imageFile) throws IOException {
        InventoryItem item = new InventoryItem();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(BigDecimal.valueOf(price));
        item.setSold(false);
        item.setItemType(itemTypeRepository.findById(itemTypeId).get());

        InventoryItem savedItem = inventoryItemRepository.save(item);

        if (imageFile != null && !imageFile.isEmpty()) {
            ItemPicture picture = new ItemPicture();
            picture.setInventoryItem(savedItem);
            picture.setImageUrl("/images/" + imageFile.getOriginalFilename());
            itemPictureRepository.save(picture);

            // Save the uploaded image to your static/images folder
            String uploadDir = new java.io.File("target/classes/static/images").getAbsolutePath();
imageFile.transferTo(new java.io.File(uploadDir, imageFile.getOriginalFilename()));
        }
    }

    public void deleteInventoryItem(long id){
        inventoryItemRepository.deleteById(id);
    }

    public void updateInventoryItem(long id, long itemTypeId, String name, String description, double price, MultipartFile imageFile) throws IOException {
        var item = inventoryItemRepository.findById(id).orElseThrow();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(BigDecimal.valueOf(price));
        item.setItemType(itemTypeRepository.findById(itemTypeId).get());
        InventoryItem savedItem = inventoryItemRepository.save(item);

        if (imageFile != null && !imageFile.isEmpty()) {
            ItemPicture picture = new ItemPicture();
            picture.setInventoryItem(savedItem);
            picture.setImageUrl("/images/" + imageFile.getOriginalFilename());
            itemPictureRepository.save(picture);

            // Save the uploaded image to your static/images folder
            String uploadDir = new java.io.File("target/classes/static/images").getAbsolutePath();
            imageFile.transferTo(new java.io.File(uploadDir, imageFile.getOriginalFilename()));
        }
    }

    public List<ItemType> getAllItemTypes(){
        return itemTypeRepository.findAll();
    }

    public void promoteUserToAdmin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsAdmin(true);
        userRepository.save(user);
    }

    public List<SaleDTO> getSalesReport() {
        return orderRepository.findAll().stream()
                .flatMap(order -> order.getItems().stream()
                        .map(item -> new SaleDTO(item.getName(), item.getPrice(), order.getUser().getUsername(), order.getId())))
                .collect(Collectors.toList());
    }

    public void exportSalesReportToCsv(HttpServletResponse response) throws IOException {
        List<SaleDTO> sales = getSalesReport();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"sales_report.csv\"");

        var writer = response.getWriter();
        writer.println("Item Name,Price,Username,Order ID");

        for (SaleDTO sale : sales) {
            writer.println(String.format("\"%s\",%.2f,\"%s\",%d",
                    sale.getItemName(), sale.getPrice(), sale.getUsername(), sale.getOrderId()));
        }
        writer.flush();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}