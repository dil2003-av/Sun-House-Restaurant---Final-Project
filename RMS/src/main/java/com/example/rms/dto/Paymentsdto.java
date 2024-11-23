package com.example.rms.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString


    public class Paymentsdto {
    private String paymentId;
    private String paymentMethod;
    private Double paymentAmount;
    private Date paymentDate;

}