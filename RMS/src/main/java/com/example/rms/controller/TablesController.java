package com.example.rms.controller;

import com.example.rms.Tm.TablesTm;
import com.example.rms.dto.Tablesdto;
import com.example.rms.model.TablesModel;
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

public class TablesController implements Initializable {

    @FXML
    private Label Employeeid;

    @FXML
    private AnchorPane TableAPid;

    @FXML
    private Button btnBack;

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
    private TableColumn<TablesTm, String> colTableId;

    @FXML
    private TableColumn<TablesTm, String> colTableNumber;

    @FXML
    private TableColumn<TablesTm, String> colCapacity;

    @FXML
    private TableColumn<TablesTm, String> colLocation;

    @FXML
    private TableColumn<TablesTm, String> colStatus;

    @FXML
    private TableView<TablesTm> tblTable;

    @FXML
    private TextField txtCapacity;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtTableId;

    @FXML
    private TextField txtTableNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTableId.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        colTableNumber.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("tableCapacity"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("tableLocation"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("tableStatus"));

        try {
            txtTableId.setText(TablesModel.getNextTableId());
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setupEnterKeyListeners();
    }

    private void setupEnterKeyListeners() {
        txtTableId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtTableNumber.requestFocus(); // Move focus to the Name field
            }
        });

        txtTableNumber.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCapacity.requestFocus(); // Move focus to the Address field
            }
        });

        txtCapacity.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtLocation.requestFocus(); // Move focus to the Phone field
            }
        });

        txtLocation.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtStatus.requestFocus(); // Move focus to the Email field
            }
        });
//
//        txtCategory.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                txtKitchenSection.requestFocus(); // Move focus to the User ID field
//            }
//        });

        txtStatus.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSave.fire(); // Trigger Save button's action
            }
        });
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        TableAPid.getChildren().clear();
        TableAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String tableId = txtTableId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this table?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = TablesModel.deleteTable(tableId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Table deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete table!").show();
            }
        }
    }

    @FXML
    void GenerateOA(ActionEvent event) {
        // Code to generate report can be added here.
    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void SaveOA(ActionEvent event) throws SQLException {
        Tablesdto table = new Tablesdto(
                txtTableId.getText(),
                txtTableNumber.getText(),
                txtCapacity.getText(),
                txtLocation.getText(),
                txtStatus.getText()
        );

        boolean isSaved = TablesModel.saveTable(table);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Table saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save table!").show();
        }
    }

    @FXML
    void UpdateOA(ActionEvent event) throws SQLException {
        Tablesdto table = new Tablesdto(
                txtTableId.getText(),
                txtTableNumber.getText(),
                txtCapacity.getText(),
                txtLocation.getText(),
                txtStatus.getText()
        );

        boolean isUpdated = TablesModel.updateTable(table);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Table updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update table!").show();
        }
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        TablesTm selectedItem = tblTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtTableId.setText(selectedItem.getTableId());
            txtTableNumber.setText(selectedItem.getTableNumber());
            txtCapacity.setText(selectedItem.getTableCapacity());
            txtLocation.setText(selectedItem.getTableLocation());
            txtStatus.setText(selectedItem.getTableStatus());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtTableId.setText(TablesModel.getNextTableId());
        txtTableNumber.clear();
        txtCapacity.clear();
        txtLocation.clear();
        txtStatus.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<Tablesdto> tables = TablesModel.getAllTables();
        ObservableList<TablesTm> tableTMs = FXCollections.observableArrayList();

        for (Tablesdto table : tables) {
            tableTMs.add(new TablesTm(
                    table.getTableId(),
                    table.getTableNumber(),
                    table.getTableCapacity(),
                    table.getTableLocation(),
                    table.getTableStatus()
            ));
        }
        tblTable.setItems(tableTMs);
    }

    @FXML
    private TextField txtSearchTableId; // TextField for searching Table by TableId

    @FXML
    private Button btnSearch; // Button to trigger search action

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String tableId = txtSearchTableId.getText(); // Get the Table ID entered in the search field

        if (tableId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a Table ID to search!").show();
            return;
        }

        // Search for the table using TablesModel
        Tablesdto table = TablesModel.searchTable(tableId);

        if (table != null) {
            // Populate the fields with the search result
            txtTableId.setText(table.getTableId());
            txtTableNumber.setText(table.getTableNumber());
            txtCapacity.setText(table.getTableCapacity());
            txtLocation.setText(table.getTableLocation());
            txtStatus.setText(table.getTableStatus());

            // Disable the Save button and enable Update and Delete buttons
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        } else {
            new Alert(Alert.AlertType.ERROR, "Table not found!").show();
            refreshPage();
        }
    }

}
