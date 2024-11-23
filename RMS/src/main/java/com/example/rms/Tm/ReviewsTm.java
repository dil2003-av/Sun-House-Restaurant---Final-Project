package com.example.rms.Tm;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ReviewsTm {
    private String reviewId;
    private String customerId;
    private String menuItemId;
    private String ratings;
    private String comments;
    private Date reviewDate;


}
