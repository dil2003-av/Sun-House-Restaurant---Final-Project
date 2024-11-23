package com.example.rms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PurchaseItmsdto {
    private String purchaseItemId;
    private String inventoryItemId;
    private String purchaseId;
    private Double purchasePrice;
    private String quantity;
    private String status;


}
