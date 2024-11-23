package com.example.rms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logoroot.jpg"))); // Open wenakota logo eka penweemt task Bar eke.
        stage.setScene(scene);
        stage.setTitle("Sun House Restaurant");
        stage.setResizable(false);  // Stage eke size eka change krgn bri kirimat
        stage.show();

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/rms/Login.fxml"));
//        Parent root = loader.load();
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//    }
    }
}


