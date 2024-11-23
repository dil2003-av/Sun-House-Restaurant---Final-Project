package com.example.rms.Tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class OrdersTm {
 private String orderId;
 private String reservationId;
 private String customerId;
 private String paymentId;
 private Date orderDate;
 private Double totalAmount;
 private String orderStatus;
 private String orderType;


// public OrdersTm(String orderId, String customerId, Date orderDate, Double totalAmount, String orderType, String orderStatus, String reservationId, String paymentId) {
// }
}
