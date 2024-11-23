package com.example.rms.Tm;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderItemsTm {
    private String orderItemId;
    private String orderId;
    private String menuItemId;
    private String quantity;
    private double price;

}
