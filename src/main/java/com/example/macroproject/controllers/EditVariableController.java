package com.example.macroproject.controllers;

import com.example.macroproject.variables.Variable;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditVariableController extends FXMLController {

    private boolean isEditing = false;
    private Variable editedVariable;

    @FXML
    ComboBox<Class> comboBox;
    @FXML
    Button okButton;
    @FXML
    Button cancelButton;
    @FXML
    TextField nameTextField;
    @FXML
    TextField valueTextField;

    @FXML
    public void initialize() {
        for (Class variableTypClass : Variable.classesWithVariables) {
            comboBox.getItems().add(variableTypClass);
        }
    }


    void initData(Variable variable) {
        comboBox.getSelectionModel().select(variable.getValueType());
        nameTextField.appendText(variable.name);
        valueTextField.appendText(variable.getValue().toString());
        this.editedVariable = variable;

        isEditing = true;
        comboBox.setDisable(true);
    }

    @FXML
    protected void submitVariable() {
        Variable variable = Variable.checkVariableReference(valueTextField.getText(), comboBox.getValue(), false);

        if (variable != null) {
            variable.name = nameTextField.getText();
            if (!isEditing) {
                Variable.addNewVariable(variable);
                okButton.getScene().getWindow().hide();
            } else {
                Variable.getVariable(editedVariable.name).setToVariable(variable);
                okButton.getScene().getWindow().hide();
            }
        }
    }
}