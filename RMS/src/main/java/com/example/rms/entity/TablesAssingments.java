package com.example.rms.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TablesAssingments {
    private String tableAssignmentId;
    private String tableId;
    private String reservationId;
    private LocalDateTime assessmentDate;
}
