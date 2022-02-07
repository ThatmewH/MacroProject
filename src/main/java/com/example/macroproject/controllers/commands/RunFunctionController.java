package com.example.macroproject.controllers.commands;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.logic.IfCommand;
import com.example.macroproject.commands.logic.RunFunctionCommand;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RunFunctionController extends CommandController {

    public RunFunctionController() {
        stageWidth = 225;
        stageHeight = 125;
    }

    @FXML
    TextField inputFunctionName;

    @Override
    protected Command getInputs() {
        try {
            StringVariable functionName = (StringVariable) Variable.checkVariableReference(inputFunctionName.getText(), String.class, true);

            try {
                return new RunFunctionCommand(currentCommandFunction, functionName);
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