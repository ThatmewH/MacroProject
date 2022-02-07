package com.example.macroproject.controllers.commands;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.logic.IfCommand;
import com.example.macroproject.commands.logic.LogicCommand;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LogicController extends CommandController {

    public LogicController() {
        stageWidth = 225;
        stageHeight = 125;
    }

    @FXML
    TextField inputLogic;

    @Override
    protected Command getInputs() {
        try {
            StringVariable logicCommand = (StringVariable) Variable.checkVariableReference(inputLogic.getText(), String.class, true);

            try {
                return new LogicCommand(currentCommandFunction, logicCommand);
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