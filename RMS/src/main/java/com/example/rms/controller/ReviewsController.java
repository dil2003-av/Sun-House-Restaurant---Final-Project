package com.example.rms.controller;

import com.example.rms.Tm.ReviewsTm;
import com.example.rms.dto.Reviewsdto;
import com.example.rms.model.ReviewsModel;
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

public class ReviewsController implements Initializable {

    @FXML
    private Button Deleteid;

    @FXML
    private AnchorPane ReviewsAPid;

    @FXML
    private Button Saveid;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<ReviewsTm, String> colComments;

    @FXML
    private TableColumn<ReviewsTm, String> colCustomerID;

    @FXML
    private TableColumn<ReviewsTm, String> colMenuItemID;

    @FXML
    private TableColumn<ReviewsTm, String> colRating;

    @FXML
    private TableColumn<ReviewsTm, Date> colReviewDate;

    @FXML
    private TableColumn<ReviewsTm, String> colReviewID;

    @FXML
    private Button generatereportid;

    @FXML
    private Button resetid;

    @FXML
    private TableView<ReviewsTm> tblReview;

    @FXML
    private TextField txtComments;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtMenuItemID;

    @FXML
    private TextField txtRating;

    @FXML
    private TextField txtReviewDate;

    @FXML
    private TextField txtReviewID;

    @FXML
    private Button updateid;

    private void showDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Timeline dateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDate today = LocalDate.now(); // Get current date
            txtReviewDate.setText(today.format(dateFormatter)); // Set it to the date label
        }));

        dateTimeline.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        dateTimeline.play(); // Start the timeline for the date
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colReviewID.setCellValueFactory(new PropertyValueFactory<>("reviewId"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colMenuItemID.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("ratings"));
        colComments.setCellValueFactory(new PropertyValueFactory<>("comments"));
        colReviewDate.setCellValueFactory(new PropertyValueFactory<>("reviewDate"));

        try {
            txtReviewID.setText(ReviewsModel.getNextReviewId());
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        showDate();
        setupEnterKeyListeners();
    }

    private void setupEnterKeyListeners() {
        txtReviewID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCustomerID.requestFocus(); // Move focus to the Name field
            }
        });

        txtCustomerID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtMenuItemID.requestFocus(); // Move focus to the Address field
            }
        });

        txtMenuItemID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtRating.requestFocus(); // Move focus to the Phone field
            }
        });

        txtRating.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtComments.requestFocus(); // Move focus to the Email field
            }
        });

        txtComments.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtReviewDate.requestFocus(); // Move focus to the User ID field
            }
        });

        txtReviewDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Saveid.fire(); // Trigger Save button's action
            }
        });
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        ReviewsAPid.getChildren().clear();
        ReviewsAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String reviewId = txtReviewID.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this review?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = ReviewsModel.deleteReview(reviewId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Review deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete review!").show();
            }
        }
    }

    @FXML
    void OnClickTable(MouseEvent event) {
        ReviewsTm selectedItem = tblReview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtReviewID.setText(selectedItem.getReviewId());
            txtCustomerID.setText(selectedItem.getCustomerId());
            txtMenuItemID.setText(selectedItem.getMenuItemId());
            txtRating.setText(selectedItem.getRatings());
            txtComments.setText(selectedItem.getComments());
            txtReviewDate.setText(selectedItem.getReviewDate().toString());

            Saveid.setDisable(true);
            Deleteid.setDisable(false);
            updateid.setDisable(false);
        }
    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void SaveOA(ActionEvent event) throws SQLException {
        Reviewsdto review = new Reviewsdto(
                txtReviewID.getText(),
                txtCustomerID.getText(),
                txtMenuItemID.getText(),
                txtRating.getText(),
                txtComments.getText(),
                Date.valueOf(txtReviewDate.getText())
        );

        boolean isSaved = ReviewsModel.saveReview(review);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Review saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save review!").show();
        }
    }

    @FXML
    void generatereportOA(ActionEvent event) {
        // Implement report generation logic here if needed
    }

    @FXML
    void updateOA(ActionEvent event) throws SQLException {
        Reviewsdto review = new Reviewsdto(
                txtReviewID.getText(),
                txtCustomerID.getText(),
                txtMenuItemID.getText(),
                txtRating.getText(),
                txtComments.getText(),
                Date.valueOf(txtReviewDate.getText())
        );

        boolean isUpdated = ReviewsModel.updateReview(review);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Review updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update review!").show();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtReviewID.setText(ReviewsModel.getNextReviewId());
        txtCustomerID.clear();
        txtMenuItemID.clear();
        txtRating.clear();
        txtComments.clear();
        txtReviewDate.clear();

        Saveid.setDisable(false);
        Deleteid.setDisable(true);
        updateid.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<Reviewsdto> reviews = ReviewsModel.getAllReviews();
        ObservableList<ReviewsTm> reviewTMs = FXCollections.observableArrayList();

        for (Reviewsdto review : reviews) {
            reviewTMs.add(new ReviewsTm(
                    review.getReviewId(),
                    review.getCustomerId(),
                    review.getMenuItemId(),
                    review.getRatings(),
                    review.getComments(),
                    review.getReviewDate()
            ));
        }
        tblReview.setItems(reviewTMs);
    }

    @FXML
    private TextField txtSearchReviewID; // TextField for searching Review by Review ID

    @FXML
    private Button btnSearchReview; // Button to trigger search action

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String reviewId = txtSearchReviewID.getText(); // Get the Review ID entered in the text field

        // Reset border color for the search field
        txtSearchReviewID.setStyle(txtSearchReviewID.getStyle() + ";-fx-border-color: #7367F0;");

        if (reviewId.isEmpty()) {
            // Display warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Review ID to search!").show();
            txtSearchReviewID.setStyle(txtSearchReviewID.getStyle() + ";-fx-border-color: red;");
            return;
        }

        // Search for the review using ReviewsModel
        Reviewsdto review = ReviewsModel.searchReview(reviewId);

        if (review != null) {
            // Populate the fields with the search result
            txtReviewID.setText(review.getReviewId());
            txtCustomerID.setText(review.getCustomerId());
            txtMenuItemID.setText(review.getMenuItemId());
            txtRating.setText(review.getRatings());
            txtComments.setText(review.getComments());
            txtReviewDate.setText(review.getReviewDate().toString());

            // Disable the Save button and enable Update and Delete buttons
            Saveid.setDisable(true);
            Deleteid.setDisable(false);
            updateid.setDisable(false);
        } else {
            // Display error if review is not found
            new Alert(Alert.AlertType.ERROR, "Review not found!").show();
            refreshPage();
        }
    }


}
