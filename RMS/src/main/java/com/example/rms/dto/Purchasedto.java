package com.example.rms.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Purchasedto {
    private String purchaseId;
    private String supplierId;
    private Double purchaseTotalAmount;
    private Date purchaseDate;
}
