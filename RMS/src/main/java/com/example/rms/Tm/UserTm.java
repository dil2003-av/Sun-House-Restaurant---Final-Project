package com.example.rms.Tm;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserTm {
    private String id;
    private String name;
    private String password;
    private LocalDateTime loginTime;
    private String EmployeeId;
}
