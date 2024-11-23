package com.example.rms.controller;

import com.example.rms.Tm.TablesAssingmentsTm;
import com.example.rms.Tm.UserTm;
import com.example.rms.dto.TablesAssingmentsdto;
import com.example.rms.model.TablesAssignmentsModel;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.rms.model.TablesAssignmentsModel.getNextTableAssignmentId;

public class TablesAssignmentsController implements Initializable {

    @FXML
    private Button Deleteid;

    @FXML
    private Button Saveid;

    @FXML
    private AnchorPane TablesAssingmentsAPid;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<TablesAssingmentsTm, String> colTableAssignmentId;

    @FXML
    private TableColumn<TablesAssingmentsTm, String> colTableId;

    @FXML
    private TableColumn<TablesAssingmentsTm, String> colReservationId;

    @FXML
    private TableColumn<TablesAssingmentsTm, LocalDateTime> colAssignmentTime;

    @FXML
    private Button generatereportid;

    @FXML
    private Button resetid;

    @FXML
    private TableView<TablesAssingmentsTm> tblTableAssignments;

    @FXML
    private TextField txtTableAssignmentId;

    @FXML
    private TextField txtTableID;

    @FXML
    private TextField txtReservationId;

    @FXML
    private TextField txtAssignmentTime;

    @FXML
    private Button updateid;

    @FXML
    private TextField txtSearchTableAssignmentId;  // Add this for the search input

    @FXML
    private Button btnSearch;  // Add this for the search button


    //    private void showTime() {
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//
//        Timeline timeTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
//            LocalTime now = LocalTime.now();
//            txtAssignmentTime.setText(now.format(timeFormatter));
//        }));
//
//        timeTimeline.setCycleCount(Animation.INDEFINITE);
//        timeTimeline.play();
//    }
private void showCurrentDateTime() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Creating a Timeline to update the time every second
    Timeline timeTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        LocalDateTime now = LocalDateTime.now();
        txtAssignmentTime.setText(now.format(dateTimeFormatter));  // Update the text field with current date and time
    }));

    timeTimeline.setCycleCount(Animation.INDEFINITE);  // Infinite loop to keep updating
    timeTimeline.play();  // Start the timeline
}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtTableAssignmentId.setText(getNextTableAssignmentId());
            refreshTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Saveid.setDisable(false);
        updateid.setDisable(true);
        Deleteid.setDisable(true);

        //showTime();
        showCurrentDateTime();
        initializeTable();
        setupEnterKeyListeners();


    }
    private void initializeTable() {
        colTableAssignmentId.setCellValueFactory(new PropertyValueFactory<>("tableAssignmentId"));
        colTableId.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colAssignmentTime.setCellValueFactory(new PropertyValueFactory<>("assessmentDate"));


        colAssignmentTime.setCellFactory(column -> new TableCell<TablesAssingmentsTm, LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });
    }

    private void setupEnterKeyListeners() {
        txtTableAssignmentId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtTableID.requestFocus(); // Move focus to the Name field
            }
        });

        txtTableID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtReservationId.requestFocus(); // Move focus to the Address field
            }
        });

        txtReservationId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAssignmentTime.requestFocus(); // Move focus to the Phone field
            }
        });

//        txtPrice.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                txtCategory.requestFocus(); // Move focus to the Email field
//            }
//        });
//
//        txtCategory.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                txtKitchenSection.requestFocus(); // Move focus to the User ID field
//            }
//        });

        txtAssignmentTime.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Saveid.fire(); // Trigger Save button's action
            }
        });
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        TablesAssingmentsAPid.getChildren().clear();
        TablesAssingmentsAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String tableAssignmentId = txtTableAssignmentId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Table Assignment?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            boolean isDeleted = TablesAssignmentsModel.deleteTableAssignment(tableAssignmentId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Table Assignment Deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Table Assignment...!").show();
            }
        }
    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void SaveOA(ActionEvent event) {
        try {
            // Trim the input to remove any leading/trailing spaces
            String assignmentTimeString = txtAssignmentTime.getText().trim();

            // Ensure the input format is valid before parsing
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Try parsing the assignment time
            LocalDateTime assignmentTime = LocalDateTime.parse(assignmentTimeString, formatter);

            TablesAssingmentsdto tableAssignment = new TablesAssingmentsdto(
                    txtTableAssignmentId.getText(),
                    txtTableID.getText(),
                    txtReservationId.getText(),
                    assignmentTime
            );

            // Save the table assignment to the model
            boolean isSaved = TablesAssignmentsModel.saveTableAssignment(tableAssignment);
            if (isSaved) {
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Table Assignment...!").show();
            }
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid date format. Please enter the date in 'yyyy-MM-dd HH:mm:ss' format.").show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void generatereportOA(ActionEvent event) {
        // Implement report generation logic
    }

    @FXML
    void updateOA(ActionEvent event) throws SQLException {
        String tableAssignmentId = txtTableAssignmentId.getText();
        String tableId = txtTableID.getText();
        String reservationId = txtReservationId.getText();
        LocalDateTime assignmentTime = LocalDateTime.parse(txtAssignmentTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        TablesAssingmentsdto tableAssignment = new TablesAssingmentsdto(tableAssignmentId, tableId, reservationId, assignmentTime);

        boolean isUpdated = TablesAssignmentsModel.updateTableAssignment(tableAssignment);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Table Assignment updated...!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update Table Assignment...!").show();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtAssignmentTime.setText(getNextTableAssignmentId());
        txtTableID.clear();
        txtReservationId.clear();
        txtAssignmentTime.clear();

        Saveid.setDisable(false);
        updateid.setDisable(true);
        Deleteid.setDisable(true);
    }

    private void refreshTable() {
        try {
            ArrayList<TablesAssingmentsdto> tableAssignments = TablesAssignmentsModel.getAllTableAssignments();
            ObservableList<TablesAssingmentsTm> tableAssignmentsTms = FXCollections.observableArrayList();

            for (TablesAssingmentsdto tableAssignment : tableAssignments) {
                tableAssignmentsTms.add(new TablesAssingmentsTm(
                        tableAssignment.getTableAssignmentId(),
                        tableAssignment.getTableId(),
                        tableAssignment.getReservationId(),
                        tableAssignment.getAssessmentDate()
                ));
            }
            tblTableAssignments.setItems(tableAssignmentsTms);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnClickTable(MouseEvent event) {
        TablesAssingmentsTm selectedTableAssignment = tblTableAssignments.getSelectionModel().getSelectedItem();
        if (selectedTableAssignment != null) {
            txtTableAssignmentId.setText(selectedTableAssignment.getTableAssignmentId());
            txtTableID.setText(selectedTableAssignment.getTableId());
            txtReservationId.setText(selectedTableAssignment.getReservationId());
            txtAssignmentTime.setText(selectedTableAssignment.getAssessmentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            Saveid.setDisable(true);
            updateid.setDisable(false);
            Deleteid.setDisable(false);
        }
    }
    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String tableAssignmentId = txtSearchTableAssignmentId.getText(); // Get the Table Assignment ID entered in the text field

        if (tableAssignmentId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a Table Assignment ID to search!").show();
            return;
        }

        // Search for the table assignment using TablesAssignmentsModel
        TablesAssingmentsdto tableAssignment = TablesAssignmentsModel.searchTableAssignment(tableAssignmentId);

        if (tableAssignment != null) {
            // Populate the fields with the search result
            txtTableAssignmentId.setText(tableAssignment.getTableAssignmentId());
            txtTableID.setText(tableAssignment.getTableId());
            txtReservationId.setText(tableAssignment.getReservationId());
            txtAssignmentTime.setText(tableAssignment.getAssessmentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // Disable the Save button and enable Update and Delete buttons
            Saveid.setDisable(true);
            updateid.setDisable(false);
            Deleteid.setDisable(false);
        } else {
            new Alert(Alert.AlertType.ERROR, "Table Assignment not found!").show();
            refreshPage();
        }
    }



}
