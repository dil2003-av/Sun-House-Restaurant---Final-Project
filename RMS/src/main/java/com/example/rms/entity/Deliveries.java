package com.example.rms.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Deliveries {
    private String deliveryId;
    private String orderId;
    private Date deliveryDate;
    private String deliveryStatus;
    private String deliveryAddress;


}
