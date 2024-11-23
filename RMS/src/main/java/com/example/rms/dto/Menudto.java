package com.example.rms.dto;

import lombok.*;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString

    public class Menudto {
        private String menuid;
        private String menuname;
        private String menudesc;
        private Double price;
        private String category;
        private String kitchensection;


//        public Menudto(String menuItemID, String name, String description, double price, String category, String kitchenSection) {
//    }
}
