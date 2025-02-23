package com.example.rms.controller;

import com.example.rms.Tm.ReservationsTm;
import com.example.rms.bo.BOFactory;
import com.example.rms.bo.custom.ReservationBO;
import com.example.rms.bo.custom.ReviewsBO;
import com.example.rms.dto.Reservationsdto;
import com.example.rms.model.ReservationsModel;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationsController implements Initializable {

    @FXML
    private Button Deleteid;

    @FXML
    private AnchorPane ReservationsAPid;

    @FXML
    private Button Saveid;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<ReservationsTm, String> colCustomerId;

    @FXML
    private TableColumn<ReservationsTm, Integer> colGuest;

    @FXML
    private TableColumn<ReservationsTm, String> colRequset;

    @FXML
    private TableColumn<ReservationsTm, Date> colReservationDate;

    @FXML
    private TableColumn<ReservationsTm, String> colReservationsId;

    @FXML
    private TableColumn<ReservationsTm, String> colStatus;

    @FXML
    private Button generatereportid;

    @FXML
    private Button resetid;

    @FXML
    private TableView<ReservationsTm> tblReservations;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtGuest;

    @FXML
    private TextField txtRequest;

    @FXML
    private TextField txtReservationDate;

    @FXML
    private TextField txtReservationId;

    @FXML
    private TextField txtStatus;

    @FXML
    private Button updateid;

    public ReservationBO reservationBO = (ReservationBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION);


    private void showDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Timeline dateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDate today = LocalDate.now(); // Get current date
            txtReservationDate.setText(today.format(dateFormatter)); // Set it to the date label
        }));

        dateTimeline.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        dateTimeline.play(); // Start the timeline for the date
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colReservationsId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colReservationDate.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        colGuest.setCellValueFactory(new PropertyValueFactory<>("numberOfGuests"));
        colRequset.setCellValueFactory(new PropertyValueFactory<>("specialRequest"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            txtReservationId.setText(reservationBO.getNextReservationId());
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        showDate();
        setupEnterKeyListeners();
    }
    private void setupEnterKeyListeners() {
        txtReservationId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCustomerId.requestFocus(); // Move focus to the Name field
            }
        });

        txtCustomerId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtReservationDate.requestFocus(); // Move focus to the Address field
            }
        });

        txtReservationDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtGuest.requestFocus(); // Move focus to the Phone field
            }
        });

        txtGuest.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtRequest.requestFocus(); // Move focus to the Email field
            }
        });

        txtRequest.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtStatus.requestFocus(); // Move focus to the User ID field
            }
        });

        txtStatus.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Saveid.fire(); // Trigger Save button's action
            }
        });
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        ReservationsAPid.getChildren().clear();
        ReservationsAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        String reservationId = txtReservationId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this reservation?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = reservationBO.deleteReservation(reservationId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Reservation deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete reservation!").show();
            }
        }
    }

    @FXML
    void SaveOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        Reservationsdto reservation = new Reservationsdto(
                txtReservationId.getText(),
                txtCustomerId.getText(),
                Date.valueOf(txtReservationDate.getText()),
                txtGuest.getText(),
                txtRequest.getText(),
                txtStatus.getText()
        );

        boolean isSaved = reservationBO.saveReservation(reservation);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Reservation saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save reservation!").show();
        }
    }

    @FXML
    void updateOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        Reservationsdto reservation = new Reservationsdto(
                txtReservationId.getText(),
                txtCustomerId.getText(),
                Date.valueOf(txtReservationDate.getText()),
                txtGuest.getText(),
                txtRequest.getText(),
                txtStatus.getText()
        );

        boolean isUpdated = reservationBO.updateReservation(reservation);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Reservation updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update reservation!").show();
        }
    }

    @FXML
    void OnClickTable(MouseEvent event) {
        ReservationsTm selectedItem = tblReservations.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtReservationId.setText(selectedItem.getReservationId());
            txtCustomerId.setText(selectedItem.getCustomerId());
            txtReservationDate.setText(selectedItem.getReservationDate().toString());
            txtGuest.setText(selectedItem.getNumberOfGuests());
            txtRequest.setText(selectedItem.getSpecialRequest());
            txtStatus.setText(selectedItem.getStatus());

            Saveid.setDisable(true);
            Deleteid.setDisable(false);
            updateid.setDisable(false);
        }
    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void generatereportOA(ActionEvent event) {

    }


    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();
        txtReservationId.setText(reservationBO.getNextReservationId());
        txtCustomerId.clear();
        txtReservationDate.clear();
        txtGuest.clear();
        txtRequest.clear();
        txtStatus.clear();

        Saveid.setDisable(false);
        Deleteid.setDisable(true);
        updateid.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<Reservationsdto> reservations = reservationBO.getAllReservations();
        ObservableList<ReservationsTm> reservationTMs = FXCollections.observableArrayList();

        for (Reservationsdto reservation : reservations) {
            reservationTMs.add(new ReservationsTm(
                    reservation.getReservationId(),
                    reservation.getCustomerId(),
                    reservation.getReservationDate(),
                    reservation.getNumberOfGuests(),
                    reservation.getSpecialRequest(),
                    reservation.getStatus()
            ));
        }
        tblReservations.setItems(reservationTMs);
    }

        @FXML
        private TextField txtSearchReservationId; // TextField for searching Reservation by ID

        @FXML
        private Button btnSearchReservation; // Button to trigger search action

        @FXML
        void searchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
            String reservationId = txtSearchReservationId.getText(); // Get the Reservation ID entered in the text field

            // Reset border color for the search field
            txtSearchReservationId.setStyle(txtSearchReservationId.getStyle() + ";-fx-border-color: #7367F0;");

            if (reservationId.isEmpty()) {
                // Display warning if the search field is empty
                new Alert(Alert.AlertType.WARNING, "Please enter a Reservation ID to search!").show();
                txtSearchReservationId.setStyle(txtSearchReservationId.getStyle() + ";-fx-border-color: red;");
                return;
            }

            // Search for the reservation using the ReservationsModel
            Reservationsdto reservation = ReservationsModel.searchReservation(reservationId);

            if (reservation != null) {
                // Populate the fields with the search result
                txtReservationId.setText(reservation.getReservationId());
                txtCustomerId.setText(reservation.getCustomerId());
                txtReservationDate.setText(reservation.getReservationDate().toString());
                txtGuest.setText(reservation.getNumberOfGuests());
                txtRequest.setText(reservation.getSpecialRequest());
                txtStatus.setText(reservation.getStatus());

                // Disable the Save button and enable Update/Delete buttons
                Saveid.setDisable(true);
                Deleteid.setDisable(false);
                updateid.setDisable(false);
            } else {
                // Display error if reservation is not found
                new Alert(Alert.AlertType.ERROR, "Reservation not found!").show();
                refreshPage();
            }
        }

    }
