package com.example.rms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class InventoryItems {
    private String inventoryItemId;
    private String inventoryName;
    private String inventoryDescription;
    private String inventoryQuantity;
    private String inventoryUnit;

}

