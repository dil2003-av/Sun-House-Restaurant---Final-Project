package com.example.rms.dto;


import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TablesAssingmentsdto {
    private String tableAssignmentId;
    private String tableId;
    private String reservationId;
    private LocalDateTime assessmentDate;
}
