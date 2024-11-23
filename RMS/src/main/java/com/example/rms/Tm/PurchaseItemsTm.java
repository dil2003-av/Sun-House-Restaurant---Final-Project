package com.example.rms.Tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PurchaseItemsTm {
    private String purchaseItemId;
    private String inventoryItemId;
    private String purchaseId;
    private Double purchasePrice;
    private String quantity;
    private String status;

}
