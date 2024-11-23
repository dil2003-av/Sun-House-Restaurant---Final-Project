package com.example.rms.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MenuItemIngredientsdto {
    private String menuItemId;
    private String inventoryItemId;
    private Double quantity;

}
