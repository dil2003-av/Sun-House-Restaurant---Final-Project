package com.example.rms.entity;

import lombok.*;

import java.sql.Date;

@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString


    public class Payments {
    private String paymentId;
    private String paymentMethod;
    private Double paymentAmount;
    private Date paymentDate;

}