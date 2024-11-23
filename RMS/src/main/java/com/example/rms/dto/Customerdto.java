package com.example.rms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class Customerdto {
    private String CustomerId;
    private String CustomerName;
    private String CustomerAddress;
    private String CustomerPhone;
    private String CustomerEmail;
    private String UserId;

   public Customerdto(String id, String name, String phone, String address, String email,String employeeid,String userId) {
   }


    public Customerdto(String customerID, String customerName, String customerPhone, String customerEmail, String customerAddress) {
    }
}
