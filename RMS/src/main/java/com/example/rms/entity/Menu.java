package com.example.rms.entity;

import lombok.*;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString

    public class Menu {
        private String menuid;
        private String menuname;
        private String menudesc;
        private Double price;
        private String category;
        private String kitchensection;


//        public Menudto(String menuItemID, String name, String description, double price, String category, String kitchenSection) {
//    }
}
