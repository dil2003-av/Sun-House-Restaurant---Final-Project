package com.example.rms.Tm;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentsTm {
    private String paymentId;
    private String paymentMethod;
    private Double paymentAmount;
    private Date paymentDate;


}
