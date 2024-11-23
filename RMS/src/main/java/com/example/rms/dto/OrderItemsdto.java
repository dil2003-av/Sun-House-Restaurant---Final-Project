package com.example.rms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderItemsdto {
    private String orderItemId;
    private String orderId;
    private String menuItemId;
    private String quantity;
    private double price;

    public OrderItemsdto(String orderItemId, String orderId, String menuItemId, int addToCartMenuItemQty, double amount) {
    }


}
