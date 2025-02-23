package com.example.rms.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Purchase {
    private String purchaseId;
    private String supplierId;
    private Double purchaseTotalAmount;
    private Date purchaseDate;
}
