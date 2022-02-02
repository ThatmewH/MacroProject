package com.example.macroproject.controllers.commands;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.listener.WaitForKey;
import com.example.macroproject.commands.time.WaitCommand;
import com.example.macroproject.listener.Listener;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;
import javafx.fxml.FXML;

import java.awt.*;

public class WaitForKeyController extends CommandController {

    @FXML
    Button keyButton;

    public WaitForKeyController() {
        stageWidth = 150;
        stageHeight = 125;
    }

    @FXML
    protected void onKeyButtonClick() {
        setKeyButtonText("<Press Any Key>");
        Listener.getNextKey(() -> setKeyButtonText(Listener.listeningInput), null);
    }

    private void setKeyButtonText(String label) {
        keyButton.setLabel(label);
    }

    @Override
    protected Command getInputs() {
        try {
                StringVariable key = (StringVariable) Variable.checkVariableReference(keyButton.getLabel(), String.class, true);
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
}
