package com.example.rms.Tm;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TablesAssingmentsTm {
    private String tableAssignmentId;
    private String tableId;
    private String reservationId;
    private LocalDateTime assessmentDate;


}
