package com.example.macroproject.controllers.commands;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.listener.WaitForKey;
import com.example.macroproject.listener.KeyInput;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WaitForKeyController extends CommandController {

    KeyInput listener = new KeyInput(this::fireKeyListener, null);
    @FXML
    Button keyButton;

    public WaitForKeyController() {
        stageWidth = 150;
        stageHeight = 125;
    }

    @FXML
    protected void onKeyButtonClick() {
        setKeyButtonText("<Press A Key>");
        listener.startListening();
    }

    private void setKeyButtonText(String label) {
        Platform.runLater(() -> keyButton.setText(label));
    }

    private void fireKeyListener() {
        setKeyButtonText(listener.getInputValue());
        listener.stopListening();
    }

    @Override
    protected Command getInputs() {
        try {
            StringVariable key = (StringVariable) Variable.checkVariableReference(keyButton.getText(), String.class, true);
            try {
                return new WaitForKey(currentCommandFunction, key);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void closeWindow() {
        super.closeWindow();
        listener.removeSelf();
    }
}
