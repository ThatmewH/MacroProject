package com.example.macroproject.controllers.commands;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.logic.IfCommand;
import com.example.macroproject.commands.logic.JumpCommand;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class JumpController extends CommandController {

    public JumpController() {
        stageWidth = 225;
        stageHeight = 125;
    }

    @Override
    protected Command getInputs() {
        try {
            return new JumpCommand(currentCommandFunction);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}