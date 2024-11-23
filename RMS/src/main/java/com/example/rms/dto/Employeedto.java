package com.example.rms.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Employeedto {
    private String id;
    private String name;
    private String position;
    private String phone;
    private String email;
    private Date hireDate;

//    public Employeedto(String employeeID, String name, String position, String phone, String email, Date hireDate) {
//    }

//    public Employeedto(String string, String string1, String string2, String string3, String string4, Date date) {
//    }
}
