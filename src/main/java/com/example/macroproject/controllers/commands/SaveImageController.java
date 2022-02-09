package com.example.macroproject.controllers.commands;


import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.opencv.SaveImageCommand;
import com.example.macroproject.commands.time.WaitCommand;
import com.example.macroproject.variables.ImageVariable;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SaveImageController extends CommandController {

    @FXML
    TextField inputImageVariable;
    @FXML
    TextField inputOutputPath;

    public SaveImageController() {
        stageWidth = 240;
        stageHeight = 160;
    }

    @Override
    protected Command getInputs() {
        try {
            ImageVariable imageVariable = (ImageVariable) Variable.getVariable(inputImageVariable.getText());
            StringVariable outputPath = (StringVariable) Variable.checkVariableReference(inputOutputPath.getText(), String.class, true);

            try {
                return new SaveImageCommand(currentCommandFunction, imageVariable, outputPath);
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