package com.example.rms.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Reservations {
    private String reservationId;
    private String customerId;
    private Date reservationDate;
    private String numberOfGuests;
    private String specialRequest;
    private String status;

}
