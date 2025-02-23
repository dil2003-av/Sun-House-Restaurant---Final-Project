package com.example.rms.controller;

import com.example.rms.Tm.MenuTm;
import com.example.rms.dto.Menudto;
import com.example.rms.dto.Paymentsdto;
import com.example.rms.model.MenuModel;
import com.example.rms.model.PaymentsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button BackId;

    @FXML
    private TableColumn<MenuTm, String> CategoryId;

    @FXML
    private TableColumn<MenuTm, String> DescriptionId;

    @FXML
    private Label Employeeid;

    @FXML
    private TableColumn<MenuTm, String> KitchenId;

    @FXML
    private AnchorPane MenuAPid;

    @FXML
    private TableColumn<MenuTm, String> MenuItemId;

    @FXML
    private TableColumn<MenuTm, String> NameId;

    @FXML
    private TableColumn<MenuTm, Double> PriceId;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btngeneratereport;

    @FXML
    private TableView<MenuTm> tblMenu;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtKitchenSection;

    @FXML
    private TextField txtMenuItemId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuItemId.setCellValueFactory(new PropertyValueFactory<>("menuid"));
        NameId.setCellValueFactory(new PropertyValueFactory<>("menuname"));
        DescriptionId.setCellValueFactory(new PropertyValueFactory<>("menudesc"));
        PriceId.setCellValueFactory(new PropertyValueFactory<>("price"));
        CategoryId.setCellValueFactory(new PropertyValueFactory<>("category"));
        KitchenId.setCellValueFactory(new PropertyValueFactory<>("kitchensection"));

        try {
            String nextMenuId = MenuModel.getNextMenuItemId();
            System.out.println(nextMenuId);
            txtMenuItemId.setText(nextMenuId);
            refreshPage();  // Refresh the table to show all menu items
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupEnterKeyListeners();
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        MenuAPid.getChildren().clear();
        MenuAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String menuId = txtMenuItemId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this menu item?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = MenuModel.deleteMenuItem(menuId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Menu item deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete menu item!").show();
            }
        }

    }

    @FXML
    void GenerateOA(ActionEvent event) {

    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException {
        refreshPage();
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtMenuItemId.setText(MenuModel.getNextMenuItemId());
        txtName.clear();
        txtDescription.clear();
        txtPrice.clear();
        txtCategory.clear();
        txtKitchenSection.clear();


        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<Menudto> menuItems = MenuModel.getAllMenuItems();
        ObservableList<MenuTm> menuTMS = FXCollections.observableArrayList();

        for (Menudto menuDto : menuItems) {
            menuTMS.add(new MenuTm(
                    menuDto.getMenuid(),
                    menuDto.getMenuname(),
                    menuDto.getMenudesc(),
                    menuDto.getPrice(),
                    menuDto.getCategory(),
                    menuDto.getKitchensection()
            ));
        }
        tblMenu.setItems(menuTMS);
    }


    @FXML
    void SaveOA(ActionEvent event) throws SQLException {
        String id = txtMenuItemId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String category = txtCategory.getText();
        String kitchenSection = txtKitchenSection.getText();

        Menudto menuDto = new Menudto(id, name, description, price, category, kitchenSection);
        boolean isSaved = MenuModel.saveMenuItem(menuDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Menu item saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save menu item!").show();
        }

    }

    @FXML
    void UpdateOA(ActionEvent event) throws SQLException {
        String id = txtMenuItemId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String category = txtCategory.getText();
        String kitchenSection = txtKitchenSection.getText();

        Menudto menuDto = new Menudto(id, name, description, price, category, kitchenSection);
        boolean isUpdated = MenuModel.updateMenuItem(menuDto);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Menu item updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update menu item!").show();
        }

    }



    @FXML
    void onMouseClicked(MouseEvent event) throws SQLException {
        MenuTm selectedItem = tblMenu.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtMenuItemId.setText(selectedItem.getMenuid());
            txtName.setText(selectedItem.getMenuname());
            txtDescription.setText(selectedItem.getMenudesc());
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));
            txtCategory.setText(selectedItem.getCategory());
            txtKitchenSection.setText(selectedItem.getKitchensection());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }


    @FXML
    private TextField txtSearchMenuItemId;

    @FXML
    private Button btnSearchMenuItem;

    @FXML
    void searchOnAction(ActionEvent event) throws Exception {
        String menuItemId = txtSearchMenuItemId.getText();

        // Reset border color for the search field
        txtSearchMenuItemId.setStyle(txtSearchMenuItemId.getStyle() + ";-fx-border-color: #7367F0;");

        if (menuItemId.isEmpty()) {
            // Display warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Menu Item ID to search!").show();
            txtSearchMenuItemId.setStyle(txtSearchMenuItemId.getStyle() + ";-fx-border-color: red;");
            return;
        }

        Menudto menuItem = MenuModel.searchMenuItem(menuItemId);

        if (menuItem != null) {

            txtName.setText(menuItem.getMenuname());
            txtDescription.setText(menuItem.getMenudesc());
            txtPrice.setText(String.valueOf(menuItem.getPrice()));
            txtCategory.setText(menuItem.getCategory());
            txtKitchenSection.setText(menuItem.getKitchensection());

            // Disable the Save button and enable Update/Delete buttons
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        } else {
            // Display error if payment is not found
            new Alert(Alert.AlertType.ERROR, "Payment not found!").show();
            refreshPage();
        }
    }

    private void setupEnterKeyListeners() {
        txtMenuItemId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus(); // Move focus to the Name field
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDescription.requestFocus(); // Move focus to the Address field
            }
        });

        txtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPrice.requestFocus(); // Move focus to the Phone field
            }
        });

        txtPrice.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCategory.requestFocus(); // Move focus to the Email field
            }
        });

        txtCategory.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtKitchenSection.requestFocus(); // Move focus to the User ID field
            }
        });

        txtKitchenSection.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSave.fire(); // Trigger Save button's action
            }
        });
    }

}


