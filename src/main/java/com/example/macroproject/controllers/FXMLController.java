package com.example.macroproject.controllers;

import com.example.macroproject.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLController {
    protected Object[] openNewStage(String fxmlFile, String name, int width, int height) {
        try {
            Object[] returnObjects = new Object[2];

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle(name);
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setScene(scene);

            returnObjects[0] = stage;
            returnObjects[1] = fxmlLoader;

            return returnObjects;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
