package com.example.rms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class Customer {
    private String CustomerId;
    private String CustomerName;
    private String CustomerAddress;
    private String CustomerPhone;
    private String CustomerEmail;
    private String UserId;

   public Customer(String id, String name, String phone, String address, String email, String employeeid, String userId) {
   }


    public Customer(String customerID, String customerName, String customerPhone, String customerEmail, String customerAddress) {
    }
}
