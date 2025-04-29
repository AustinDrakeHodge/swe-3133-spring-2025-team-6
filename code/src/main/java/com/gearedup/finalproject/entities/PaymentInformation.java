package com.gearedup.finalproject.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payment_information")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PaymentInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String creditCardNumber;
    private String shippingAddress;
    private String phoneNumber;
    private String shippingSpeed;
    private BigDecimal shippingCost;
    public String getCreditCardLast4() {
    if (creditCardNumber != null && creditCardNumber.length() >= 4) {
        return creditCardNumber.substring(creditCardNumber.length() - 4);
    }
    return "";
}
}