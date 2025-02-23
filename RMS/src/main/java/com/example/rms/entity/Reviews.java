package com.example.rms.entity;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Reviews {
    private String reviewId;
    private String customerId;
    private String menuItemId;
    private String ratings;
    private String comments;
    private Date reviewDate;

}
