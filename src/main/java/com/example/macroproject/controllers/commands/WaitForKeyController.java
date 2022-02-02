package com.example.macroproject.controllers.commands;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.listener.WaitForKey;
import com.example.macroproject.listener.Listener;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WaitForKeyController extends CommandController {

    Listener listener = new Listener();
    @FXML
    Button keyButton;

    public WaitForKeyController() {
        stageWidth = 150;
        stageHeight = 125;
    }

    @FXML
    protected void onKeyButtonClick() {
        setKeyButtonText("<Press Any Key>");

        listener.getNextKey(() -> setKeyButtonText(listener.listeningInput), null);
    }

    private void setKeyButtonText(String label) {
        Platform.runLater(() -> keyButton.setText(label));

    }

    @Override
    protected Command getInputs() {
        try {
                StringVariable key = (StringVariable) Variable.checkVariableReference(keyButton.getText(), String.class, true);
            try {
                return new WaitForKey(0, currentCommandFunction, key);
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
        listener.stopListening();
    }
}
