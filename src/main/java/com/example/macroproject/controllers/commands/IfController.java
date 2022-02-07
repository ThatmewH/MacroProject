package com.example.macroproject.controllers.commands;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.logic.IfCommand;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class IfController extends CommandController {

    public IfController() {
        stageWidth = 225;
        stageHeight = 125;
    }

    @FXML
    TextField inputIfLogic;

    @Override
    protected Command getInputs() {
        try {
            StringVariable ifLogic = (StringVariable) Variable.checkVariableReference(inputIfLogic.getText(), String.class, true);

            try {
                return new IfCommand(currentCommandFunction, ifLogic);
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