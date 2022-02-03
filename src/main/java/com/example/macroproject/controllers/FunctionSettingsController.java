package com.example.macroproject.controllers;

import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.listener.KeyInput;
import com.example.macroproject.variables.Variable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;


public class FunctionSettingsController extends FXMLController {
    MainController mainController;
    CommandFunction commandFunction;

    KeyInput startButtonListener = new KeyInput(this::startButtonCallback, null);
    KeyInput stopButtonListener = new KeyInput(this::stopButtonCallback, null);

    @FXML
    TextField searchVariable;
    @FXML
    ListView<Variable> variableList;
    @FXML
    Button startButton;
    @FXML
    Button stopButton;

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

    void initData(MainController mainController, CommandFunction commandFunction) {
        this.mainController = mainController;
        this.commandFunction = commandFunction;
        addAllVariables();
        initCommandFunctionData();
    }

    void initCommandFunctionData() {
        String runKey = commandFunction.getStartListener().getListeningKey().getValue();
        String stopKey = commandFunction.getStopListener().getListeningKey().getValue();

        if (runKey.equals("")) {
            startButton.setText("<Click To Set>");
        } else {
            startButton.setText(runKey);
        }
        if (stopKey.equals("")) {
            stopButton.setText("<Click To Set>");
        } else {
            stopButton.setText(stopKey);
        }
    }

    @FXML
    protected void onStartButtonClick() {
        startButtonListener.startListening();
        startButton.setText("<Press Any Key>");
    }

    @FXML
    protected void onStopButtonClick() {
        stopButtonListener.startListening();
        stopButton.setText("<Press Any Key>");
    }

    private void startButtonCallback() {
        String listenKey = startButtonListener.getInputValue();
        commandFunction.getStartListener().getListeningKey().setValue(listenKey);

        Platform.runLater(() -> startButton.setText(listenKey));
        startButtonListener.stopListening();
    }

    private void stopButtonCallback() {
        String listenKey = stopButtonListener.getInputValue();
        commandFunction.getStopListener().getListeningKey().setValue(listenKey);

        Platform.runLater(() -> stopButton.setText(listenKey));
        stopButtonListener.stopListening();
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

    private void onVariableStageClose() {
        refreshList();
        mainController.refreshTabCommands(mainController.getSelectedTab());
    }

    @FXML
    protected void openNewVariableStage() {
        Object[] stageAndLoader = openNewStage("variable-edit.fxml", "Edit Variable", 250, 150);
        Stage stage = (Stage) stageAndLoader[0];
        FXMLLoader loader = (FXMLLoader) stageAndLoader[1];
        stage.setOnHiding(windowEvent -> onVariableStageClose());
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
                    onVariableStageClose();
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