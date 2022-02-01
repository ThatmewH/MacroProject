package com.example.macroproject.controllers;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.variables.Variable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;


public class VariableListController extends FXMLController {
    MainController mainController;

    @FXML
    TextField searchVariable;
    @FXML
    ListView<Variable> variableList;

    @FXML
    public void initialize() {
        searchVariable.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            updateSearchResults(e);
            e.consume();
        });
    }

    protected void addAllVariables() {
        for (Variable variable : Variable.variables) {
            variableList.getItems().add(variable);
        }
    }

    protected void removeAllVariables() {
        variableList.getItems().removeAll(variableList.getItems());
    }

    protected void refreshList() {
        removeAllVariables();
        addAllVariables();
        updateSearchResults(null);
    }

    void initData(MainController mainController) {
        this.mainController = mainController;
        addAllVariables();
    }

    public void updateSearchResults(KeyEvent letter) {
        if (letter != null) {
            searchVariable.appendText(letter.getCharacter());
        }

        List<Variable> variables = variableList.getItems().stream().toList();

        if (variables.size() == 0) {
            variables = Variable.variables.stream().toList();
        }

        removeAllRegisteredCommands();

        if (searchVariable.getCharacters().length() == 0) {
            addAllRegisteredCommands();
        } else {
            for (Variable registeredCommand : variables) {
                if (registeredCommand.name.toLowerCase().contains(searchVariable.getCharacters().toString().toLowerCase())) {
                    variableList.getItems().add(registeredCommand);
                }
            }
        }
    }

    protected void addAllRegisteredCommands() {
        for (Variable variable : Variable.variables) {
            variableList.getItems().add(variable);
        }
    }

    protected void removeAllRegisteredCommands() {
        variableList.getItems().removeAll(variableList.getItems());
    }

    @FXML
    protected void openNewVariableStage() {
        Object[] stageAndLoader = openNewStage("variable-edit.fxml", "Edit Variable", 250, 150);
        Stage stage = (Stage) stageAndLoader[0];
        FXMLLoader loader = (FXMLLoader) stageAndLoader[1];
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                refreshList();
            }
        });
        stage.show();
    }

    @FXML
    protected void openEditVariableStage() {
        if (variableList.getSelectionModel().getSelectedItem() != null) {
            Object[] stageAndLoader = openNewStage("variable-edit.fxml", "Edit Variable", 250, 150);
            Stage stage = (Stage) stageAndLoader[0];
            FXMLLoader loader = (FXMLLoader) stageAndLoader[1];
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    refreshList();
                }
            });

            EditVariableController controller = loader.getController();
            Variable selectedVariable = variableList.getSelectionModel().getSelectedItem();
            controller.initData(selectedVariable);
            stage.show();
        }
    }

    @FXML
    protected void deleteVariable() {
        if (variableList.getSelectionModel().getSelectedItem() != null) {
            Variable.deleteVariable(variableList.getSelectionModel().getSelectedItem().name);
        }
        refreshList();
    }
}