package com.example.rms.Tm;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DeliveriesTm {
    private String deliveryId;
    private String orderId;
    private Date deliveryDate;
    private String deliveryStatus;
    private String deliveryAddress;

}
