package com.example.rms.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private String id;
    private String name;
    private String password;
    private LocalDateTime loginTime;
    private String EmployeeId;

    public User(String text, String text1, String text2, String text3, String text4) {
    }

//    public Userdto(String text, String text1, String text2, String text3, String text4) {
//    }


//    public Userdto(String id, String Name, String password, LocalDateTime LoginTime, String EmployeeID) {
//    }
}
