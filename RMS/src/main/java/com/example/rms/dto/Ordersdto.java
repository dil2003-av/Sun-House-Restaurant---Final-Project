package com.example.rms.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class Ordersdto {
    private String orderId;
    private String reservationId;
    private String customerId;
    private String paymentId;
    private Date orderDate;
    private Double totalAmount;
    private String orderStatus;
    private String orderType;


    public Ordersdto(String text, String text1, Date date, double v, String text2, String text3, String text4, String text5) {
    }

    public Ordersdto(String orderId, String reservationId,String customerId,String paymentId, LocalDate orderDate, double totalAmount, String orderStatus, String orderType) {
    }




}
