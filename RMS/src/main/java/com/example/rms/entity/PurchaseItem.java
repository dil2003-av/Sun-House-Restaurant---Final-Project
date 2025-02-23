package com.example.rms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PurchaseItem {
    private String purchaseItemId;
    private String inventoryItemId;
    private String purchaseId;
    private Double purchasePrice;
    private String quantity;
    private String status;


}
