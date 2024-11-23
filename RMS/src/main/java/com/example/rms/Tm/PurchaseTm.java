package com.example.rms.Tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PurchaseTm {
    private String purchaseId;
    private String supplierId;
    private Double purchaseTotalAmount;
    private Date purchaseDate;

}
