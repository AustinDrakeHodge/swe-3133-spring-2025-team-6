package com.gearedup.finalproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SaleDTO {
    private String itemName;
    private BigDecimal price;
    private String username;
    private Long orderId;
}