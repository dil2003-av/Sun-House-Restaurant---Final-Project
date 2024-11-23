module com.example.rms {
    requires javafx.controls;
   // requires javafx.fxml;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires javafx.base;
    requires net.sf.jasperreports.core;
    requires java.mail;
    requires jbcrypt;


    opens com.example.rms.controller to javafx.fxml;
    opens com.example.rms.Tm to javafx.base;
    //opens com.example.rms.model to javafx.fxml;
    exports com.example.rms;
}