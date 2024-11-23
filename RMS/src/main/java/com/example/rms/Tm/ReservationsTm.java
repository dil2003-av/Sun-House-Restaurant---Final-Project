package com.example.rms.Tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ReservationsTm {
    private String reservationId;
    private String customerId;
    private Date reservationDate;
    private String numberOfGuests;
    private String specialRequest;
    private String status;


}
