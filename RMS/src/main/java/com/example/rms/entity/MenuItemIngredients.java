package com.example.rms.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MenuItemIngredients {
    private String menuItemId;
    private String inventoryItemId;
    private Double quantity;

}
