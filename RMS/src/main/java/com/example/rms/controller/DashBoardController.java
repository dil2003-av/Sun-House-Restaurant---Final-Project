package com.example.rms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardController {

    @FXML
    private AnchorPane DashBoardAPId;

    @FXML
    private Button btnBack;

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        DashBoardAPId.getChildren().clear();
        DashBoardAPId.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));

    }

}
