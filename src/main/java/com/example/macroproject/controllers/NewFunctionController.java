package com.example.macroproject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewFunctionController extends FXMLController {

    MainController mainController;

    @FXML
    TextField functionTextField;

    @FXML
    protected void closeStage() {
        functionTextField.getScene().getWindow().hide();
    }

    @FXML
    public void initData(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    protected void submitForm() {
        try {
            this.mainController.addNewFunction(functionTextField.getText());
            closeStage();
        } catch (IllegalArgumentException e) {
            System.out.println("Function Already Exists");
        }
    }
}
