package com.example.macroproject.controllers.commands;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.listener.WaitForClick;
import com.example.macroproject.commands.listener.WaitForKey;
import com.example.macroproject.listener.KeyInput;
import com.example.macroproject.listener.MouseInput;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class WaitForClickController extends CommandController {

    MouseInput listener = new MouseInput(this::fireKeyListener, new IntegerVariable("", null), new IntegerVariable("", 0)
            , new IntegerVariable("", 0), new IntegerVariable("", 0));
    @FXML
    Button keyButton;

    @FXML
    TextField outputPosXTextField;
    @FXML
    TextField outputPosYTextField;
    @FXML
    TextField outputClickNumTextField;

    public WaitForClickController() {
        stageWidth = 300;
        stageHeight = 225;
    }

    @FXML
    protected void onKeyButtonClick() {
        setKeyButtonText("<Press A Key>");
        listener.startListening();
    }

    private void setKeyButtonText(String label) {
        Platform.runLater(() -> keyButton.setText(label));
    }

    private void fireKeyListener() {
        setKeyButtonText(String.valueOf(listener.getInputValue()));
        listener.stopListening();
    }

    @Override
    protected Command getInputs() {
        try {
            IntegerVariable clickButton = (IntegerVariable) Variable.checkVariableReference(keyButton.getText(), Integer.class, true);

            IntegerVariable outputPosX = !outputPosXTextField.getText().equals("")
                    ? (IntegerVariable) Variable.checkVariableReference(outputPosXTextField.getText(), Integer.class, true)
                    : new IntegerVariable("", 0);

            IntegerVariable outputPosY = !outputPosYTextField.getText().equals("")
                    ? (IntegerVariable) Variable.checkVariableReference(outputPosYTextField.getText(), Integer.class, true)
                    : new IntegerVariable("", 0);

            IntegerVariable outputClickNum = !outputClickNumTextField.getText().equals("")
                    ? (IntegerVariable) Variable.checkVariableReference(outputClickNumTextField.getText(), Integer.class, true)
                    : new IntegerVariable("", 0);

            try {
                return new WaitForClick(currentCommandFunction, clickButton
                        , outputPosX
                        , outputPosY
                        , outputClickNum);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void closeWindow() {
        super.closeWindow();
        listener.removeSelf();
    }
}
