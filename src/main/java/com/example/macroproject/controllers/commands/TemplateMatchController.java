package com.example.macroproject.controllers.commands;


import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.opencv.SaveImageCommand;
import com.example.macroproject.commands.opencv.TemplateMatchingCommand;
import com.example.macroproject.variables.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.controlsfx.control.ToggleSwitch;
import org.opencv.core.Mat;

public class TemplateMatchController extends CommandController {

    @FXML
    TextField inputBackgroundImage;
    @FXML
    TextField inputTemplateImage;
    @FXML
    TextField inputThreshhold;
    @FXML
    ToggleSwitch inputShowImage;

    @FXML
    TextField outputFoundImage;
    @FXML
    TextField outputPosX;
    @FXML
    TextField outputPosY;
    @FXML
    TextField outputWidth;
    @FXML
    TextField outputHeight;

    public TemplateMatchController() {
        stageWidth = 400;
        stageHeight = 300;
    }

    @Override
    protected Command getInputs() {
        try {
            BooleanVariable showImage = (BooleanVariable) Variable.checkVariableReference(String.valueOf(inputShowImage.isSelected())
                    , Boolean.class, true);
            ImageVariable backgroundImage = (ImageVariable) Variable.checkVariableReference(
                    inputBackgroundImage.getText(), Mat.class, true);
            ImageVariable templateImage = (ImageVariable) Variable.checkVariableReference(inputTemplateImage.getText()
                    , Mat.class, true);
            DoubleVariable threshhold = (DoubleVariable) Variable.checkVariableReference(inputThreshhold.getText()
                    , Double.class, true);


            BooleanVariable outputImageWasFound = outputFoundImage.getText().equals("")
                    ? new BooleanVariable("", false)
                    : (BooleanVariable) Variable.getVariable(outputFoundImage.getText());

            DoubleVariable posX = outputPosX.getText().equals("") ? new DoubleVariable("", -1.0)
                    : (DoubleVariable) Variable.getVariable(outputPosX.getText());

            DoubleVariable posY = outputPosY.getText().equals("") ? new DoubleVariable("", -1.0)
                    : (DoubleVariable) Variable.getVariable(outputPosY.getText());

            DoubleVariable width = outputWidth.getText().equals("") ? new DoubleVariable("", -1.0)
                    : (DoubleVariable) Variable.getVariable(outputWidth.getText());

            DoubleVariable height = outputHeight.getText().equals("") ? new DoubleVariable("", -1.0)
                    : (DoubleVariable) Variable.getVariable(outputHeight.getText());

            try {
                return new TemplateMatchingCommand(currentCommandFunction, showImage, backgroundImage, templateImage
                        , threshhold, outputImageWasFound, posX, posY, width, height);
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