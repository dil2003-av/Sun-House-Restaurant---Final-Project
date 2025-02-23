package com.example.rms.controller;

import com.example.rms.Tm.MenuItemIngredientsTm;
import com.example.rms.bo.BOFactory;
import com.example.rms.bo.custom.MenuItemIngrediantsBO;
import com.example.rms.dto.MenuItemIngredientsdto;
//import com.example.rms.model.MenuItemIngredientsModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuItemIngredientsController implements Initializable {

    @FXML
    private AnchorPane MenuItemIngredientsAPid;

    @FXML
    private TableView<MenuItemIngredientsTm> tblmenuitemingredients;

    @FXML
    private TableColumn<MenuItemIngredientsTm, String> colMenuItemID;

    @FXML
    private TableColumn<MenuItemIngredientsTm, String> colInventoryItemID;

    @FXML
    private TableColumn<MenuItemIngredientsTm, Double> colQtty;

    @FXML
    private TextField txtMenuItemId;

    @FXML
    private TextField txtInventoryItemID;

    @FXML
    private TextField txtQTY;

    @FXML
    private Button saveid;

    @FXML
    private Button Update;

    @FXML
    private Button Delete;

    @FXML
    private Button Reset;

    @FXML
    private Button Generatereport;

    @FXML
    private Button btnBack;

    public MenuItemIngrediantsBO menuItemIngrediantsBO = (MenuItemIngrediantsBO) BOFactory.getInstance().getBO(BOFactory.BOType.MENU_ITEM_INGREDIANT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMenuItemID.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        colInventoryItemID.setCellValueFactory(new PropertyValueFactory<>("inventoryItemId"));
        colQtty.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        setupEnterKeyListeners();
    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String menuItemId = txtMenuItemId.getText();
        String inventoryItemId = txtInventoryItemID.getText();
        String quantityStr = txtQTY.getText();

        if (menuItemId.isEmpty() || inventoryItemId.isEmpty() || quantityStr.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "All fields must be filled out!").show();
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityStr);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Quantity must be a valid number!").show();
            return;
        }

        MenuItemIngredientsdto dto = new MenuItemIngredientsdto(menuItemId, inventoryItemId, quantity);

        boolean isSaved = menuItemIngrediantsBO.saveMenuItemIngredient(dto);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save!").show();
        }
    }

    @FXML
    void UpdateOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        String menuItemId = txtMenuItemId.getText();
        String inventoryItemId = txtInventoryItemID.getText();
        String quantityStr = txtQTY.getText();

        if (menuItemId.isEmpty() || inventoryItemId.isEmpty() || quantityStr.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "All fields must be filled out!").show();
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityStr);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Quantity must be a valid number!").show();
            return;
        }

        MenuItemIngredientsdto dto = new MenuItemIngredientsdto(menuItemId, inventoryItemId, quantity);

        boolean isUpdated = menuItemIngrediantsBO.updateMenuItemIngredient(dto);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update!").show();
        }
    }

    @FXML
    void deleteOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        String menuItemId = txtMenuItemId.getText();
        String inventoryItemId = txtInventoryItemID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            boolean isDeleted = menuItemIngrediantsBO.deleteMenuItemIngredient(menuItemId, inventoryItemId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete!").show();
            }
        }
    }
    @FXML
    void generateOA(ActionEvent event) {

    }


    @FXML
    void ResetOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void clickOnAction(MouseEvent event) {
        MenuItemIngredientsTm selectedItem = tblmenuitemingredients.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtMenuItemId.setText(selectedItem.getMenuItemId());
            txtInventoryItemID.setText(selectedItem.getInventoryItemId());
            txtQTY.setText(String.valueOf(selectedItem.getQuantity()));

            saveid.setDisable(true);
            Delete.setDisable(false);
            Update.setDisable(false);
        }
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        MenuItemIngredientsAPid.getChildren().clear();
        MenuItemIngredientsAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        txtMenuItemId.setText("");
        txtInventoryItemID.setText("");
        txtQTY.setText("");

        saveid.setDisable(false);
        Delete.setDisable(true);
        Update.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<MenuItemIngredientsdto> dtoList = menuItemIngrediantsBO.getAllMenuItemIngredients();
        ObservableList<MenuItemIngredientsTm> tmList = FXCollections.observableArrayList();

        for (MenuItemIngredientsdto dto : dtoList) {
            MenuItemIngredientsTm tm = new MenuItemIngredientsTm(dto.getMenuItemId(), dto.getInventoryItemId(), dto.getQuantity());
            tmList.add(tm);
        }

        tblmenuitemingredients.setItems(tmList);
    }

    private void setupEnterKeyListeners() {
        txtMenuItemId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtInventoryItemID.requestFocus();
            }
        });

        txtInventoryItemID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtQTY.requestFocus();
            }
        });

        txtQTY.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                saveid.fire();
            }
        });
    }
}
