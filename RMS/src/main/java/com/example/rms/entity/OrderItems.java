package com.example.rms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderItems {
    private String orderItemId;
    private String orderId;
    private String menuItemId;
    private String quantity;
    private double price;

    public OrderItems(String orderItemId, String orderId, String menuItemId, int addToCartMenuItemQty, double amount) {
    }


    public OrderItems(String orderId, String menuItemId, String quantity, double price) {
    }
}
