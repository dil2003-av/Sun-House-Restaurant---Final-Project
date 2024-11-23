package com.example.rms.Tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CartTm {
    private String menuItemId;
    private String menuItemName;
    private double unitPrice;
    private int addToCartMenuItemQty;
    private double orderItemPrice;
    private double amount; //(addToCartMenuItemQty * orderItemPrice)
    private double totalAmount;
}