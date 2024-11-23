package com.example.rms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button Accountid;

    @FXML
    private Button Employeeid;

    @FXML
    private AnchorPane HomeAPid;

    @FXML
    private ImageView Homepageid;

    @FXML
    private Button Menuid1;

    @FXML
    private Button Ordersid;

    @FXML
    private Button Pymentid1;

    @FXML
    private Button Supplierid1;

    @FXML
    private Button Tbleid1;

    @FXML
    private VBox Vboxid1;

    @FXML
    private Button customerid1;

    @FXML
    private Button dashboardid;

    @FXML
    private Button uplogoutid;

    @FXML
    private Button InventoryID;

    @FXML
    private Button PurchaseID;

    @FXML
    private Button OrderItemsID;

    @FXML
    private Button PurchaseItemsID;

    @FXML
    private Button ReservationID;

    @FXML
    private Button TableAssingmentID;

    @FXML
    private Button ReviewsId;

    @FXML
    private Button DeliveriesId;

    @FXML
    private Button btnmenuitemingredients;


    @FXML
    void menuitemingredientsOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/MenuItemIngredients.fxml")));


    }


    @FXML
    void ReviewsOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Reviews.fxml")));

    }
    @FXML
    void DeliveriesOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Deliveries.fxml")));
    }


    @FXML
    void InventoryOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/InventoryItems.fxml")));

    }

    @FXML
    void OrderItemsOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/OrderItems.fxml")));

    }

    @FXML
    void PurchaseItemOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/PurchaseItems.fxml")));
    }

    @FXML
    void PurchasesOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Purchase.fxml")));
    }

    @FXML
    void ReservationOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Reservations.fxml")));

    }

    @FXML
    void TableAssignmentOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/TablesAssingments.fxml")));

    }


    @FXML
    void AccountsOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/User.fxml")));

    }

    @FXML
    void EmployeeOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Employee.fxml")));
    }

    @FXML
    void customerOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Customer.fxml")));
    }

    @FXML
    void uplogoutOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Login.fxml")));
    }

    @FXML
    void MenuOA(ActionEvent event) throws IOException {
       HomeAPid.getChildren().clear();
       HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Menu.fxml")));

    }


    @FXML
    void OrdersOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/OrderForm.fxml")));
    }


    @FXML
    void PaymentOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Payments.fxml")));

    }

    @FXML
    void SupplierOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Suppliers.fxml")));

    }

    @FXML
    void dashboardOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml")));
    }

    @FXML
    void tblOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Tables.fxml")));
    }

    @FXML
    void SendOA(ActionEvent event) throws IOException {
        HomeAPid.getChildren().clear();
        HomeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/SendMail.fxml")));
    }


}
