package com.example.rms.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Deliveriesdto {
    private String deliveryId;
    private String orderId;
    private Date deliveryDate;
    private String deliveryStatus;
    private String deliveryAddress;


}
