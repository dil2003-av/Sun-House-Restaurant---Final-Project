package com.example.rms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class InventoryItemsdto {
    private String inventoryItemId;
    private String inventoryName;
    private String inventoryDescription;
    private String inventoryQuantity;
    private String inventoryUnit;

}

