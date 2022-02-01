package com.example.macroproject.controllers.commands;

import com.example.macroproject.commands.Command;
import com.example.macroproject.controllers.FXMLController;
import com.example.macroproject.controllers.MainController;

import javafx.fxml.FXML;
import javafx.scene.Scene;

public abstract class CommandController extends FXMLController {
    MainController mainController;
    Scene scene;

    protected int stageWidth;
    protected int stageHeight;

    protected abstract Command getInputs();

    @FXML
    protected void closeWindow() {
        scene.getWindow().hide();
    }

    public void initData(MainController mainController, Scene scene) {
        this.mainController = mainController;
        this.scene = scene;
    }

    public int getStageHeight() {
        return stageHeight;
    }

    public int getStageWidth() {
        return stageWidth;
    }

    @FXML
    public void submitForm() {
        Command clickCommand = getInputs();
        if (clickCommand != null) {
            mainController.addCommand(clickCommand);
            closeWindow();
        }
    }
}
