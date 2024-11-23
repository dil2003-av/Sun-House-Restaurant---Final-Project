package com.example.rms.Tm;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeTm {
    private String id;
    private String name;
    private String position;
    private String phone;
    private String email;
    private Date hireDate;

}
