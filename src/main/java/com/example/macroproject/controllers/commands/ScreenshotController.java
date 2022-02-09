package com.example.macroproject.controllers.commands;


import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.opencv.TemplateMatchingCommand;
import com.example.macroproject.commands.robot.ScreenshotCommand;
import com.example.macroproject.variables.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.converter.PercentageStringConverter;
import org.controlsfx.control.ToggleSwitch;
import org.opencv.core.Mat;

public class ScreenshotController extends CommandController {

    @FXML
    TextField inputPosX;
    @FXML
    TextField inputPosY;
    @FXML
    TextField inputWidth;
    @FXML
    TextField inputHeight;
    @FXML
    TextField inputOutputImage;

    public ScreenshotController() {
        stageWidth = 300;
        stageHeight = 150;
    }

    @Override
    protected Command getInputs() {
        try {
            IntegerVariable posX = (IntegerVariable) Variable.checkVariableReference(inputPosX.getText(), Integer.class
                    , true);
            IntegerVariable posY = (IntegerVariable) Variable.checkVariableReference(inputPosY.getText(), Integer.class
                    , true);
            IntegerVariable width = (IntegerVariable) Variable.checkVariableReference(inputWidth.getText(), Integer.class
                    , true);
            IntegerVariable height = (IntegerVariable) Variable.checkVariableReference(inputHeight.getText(), Integer.class
                    , true);
            ImageVariable outputImage = (ImageVariable) Variable.getVariable(inputOutputImage.getText());

            try {
                return new ScreenshotCommand(currentCommandFunction, posX, posY, width, height, outputImage);
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