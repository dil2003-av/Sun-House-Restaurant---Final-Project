package com.example.rms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CashierHomeController {

    @FXML
    private AnchorPane CashierHomeAPid;

    @FXML
    private Button Ordersid;

    @FXML
    private Button Pymentid1;

    @FXML
    private Button Tbleid1;

    @FXML
    private Button customerid1;

    @FXML
    void OrdersOA(ActionEvent event) throws IOException {

        CashierHomeAPid.getChildren().clear();
        CashierHomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/CashierOrders.fxml")));
    }

    @FXML
    void PaymentOA(ActionEvent event) throws IOException {

        CashierHomeAPid.getChildren().clear();
        CashierHomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/CashierPayment.fxml")));
    }

    @FXML
    void customerOA(ActionEvent event) throws IOException {
        CashierHomeAPid.getChildren().clear();
        CashierHomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/CashierCustomer.fxml")));
    }

    @FXML
    void tblOA(ActionEvent event) throws IOException {
        CashierHomeAPid.getChildren().clear();
        CashierHomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/CashierTable.fxml")));
    }

    @FXML
    void uplogoutOA(ActionEvent event) throws IOException {
        CashierHomeAPid.getChildren().clear();
        CashierHomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Login.fxml")));
    }

}
