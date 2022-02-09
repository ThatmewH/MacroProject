package com.example.macroproject.controllers.commands;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.robot.ClickCommand;
import com.example.macroproject.variables.BooleanVariable;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.Variable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.controlsfx.control.ToggleSwitch;

public class ClickController extends CommandController {

    public ClickController() {
        stageWidth = 225;
        stageHeight = 240;
    }

    @FXML
    Text delayTypeText;

    @FXML
    ToggleSwitch inputCpsIsDisabled;
    @FXML
    TextField inputClickAmount;
    @FXML
    TextField inputCPSDelay;
    @FXML
    TextField inputPosX;
    @FXML
    TextField inputPosY;
    @FXML
    TextField inputClickLength;

    @Override
    protected Command getInputs() {
        try {

            IntegerVariable inputCpsDelayVar = (IntegerVariable) Variable.checkVariableReference(inputCPSDelay.getText(), Integer.class, true);
            IntegerVariable inputClickAmountVar = (IntegerVariable) Variable.checkVariableReference(inputClickAmount.getText(), Integer.class, true);
            BooleanVariable inputCpsIsDisabledVar = (BooleanVariable) Variable.checkVariableReference(String.valueOf(inputCpsIsDisabled.isSelected()), Boolean.class, true);
            IntegerVariable inputPosXVar = inputPosX.getText().equals("")
                    ? (IntegerVariable) Variable.checkVariableReference("-1", Integer.class, true)
                    : (IntegerVariable) Variable.checkVariableReference(inputPosX.getText(), Integer.class, true);
            IntegerVariable inputPosYVar = inputPosY.getText().equals("")
                    ? (IntegerVariable) Variable.checkVariableReference("-1", Integer.class, true)
                    : (IntegerVariable) Variable.checkVariableReference(inputPosY.getText(), Integer.class, true);
            IntegerVariable inputClickLengthVar = (IntegerVariable) Variable.checkVariableReference(inputClickLength.getText(), Integer.class, true);

            try {
                return new ClickCommand(currentCommandFunction, inputCpsDelayVar, inputCpsDelayVar, inputPosXVar, inputPosYVar
                        , inputClickLengthVar, inputClickAmountVar, inputCpsIsDisabledVar);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @FXML
    protected void toggleDelayType() {
        if (inputCpsIsDisabled.isSelected()) {
            delayTypeText.setText("Delay (ms): ");
        } else {
            delayTypeText.setText("Clicks / Second: ");
        }
    }
}