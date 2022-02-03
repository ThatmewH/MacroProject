package com.example.macroproject.controllers.commands;


import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.time.WaitCommand;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.Variable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WaitController extends CommandController {

    @FXML
    TextField inputWaitTime;

    public WaitController() {
        stageWidth = 150;
        stageHeight = 125;
    }

    @Override
    protected Command getInputs() {
        try {
            IntegerVariable waitTime = (IntegerVariable) Variable.checkVariableReference(inputWaitTime.getText(), Integer.class, true);

            try {
                return new WaitCommand(waitTime, currentCommandFunction);
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