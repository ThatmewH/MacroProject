package com.example.macroproject.controllers;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.RegisteredCommand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.List;


public class CommandListController extends FXMLController {
    MainController mainController;

    @FXML
    private ListView<RegisteredCommand> commandList;
    @FXML
    private Button selectButton;
    @FXML
    private TextField searchCommand;


    @FXML
    public void initialize() {
        addAllRegisteredCommands();

        commandList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<RegisteredCommand>() {
                    @Override
                    public void changed(ObservableValue<? extends RegisteredCommand> observableValue, RegisteredCommand command, RegisteredCommand t1) {
                        enableSelectCommandButton();
                    }
                }
        );

        searchCommand.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                updateSearchResults(e);
                e.consume();
            }

        });
    }

    @FXML
    protected void closeWindow() {
        commandList.getScene().getWindow().hide();
    }

    @FXML
    protected void selectCommand() {
        RegisteredCommand registeredCommand = commandList.getSelectionModel().getSelectedItem();
        this.mainController.openNewCommandStage(registeredCommand);
        closeWindow();
    }

    @FXML
    protected void enableSelectCommandButton() {
        selectButton.setDisable(false);
    }

    protected void addAllRegisteredCommands() {
        for (RegisteredCommand registeredCommand : Command.registeredCommands) {
            commandList.getItems().add(registeredCommand);
        }
    }

    protected void removeAllRegisteredCommands() {
        commandList.getItems().removeAll(commandList.getItems());
    }

    public void updateSearchResults(KeyEvent letter) {
        searchCommand.appendText(letter.getCharacter());

        List<RegisteredCommand> registeredCommands = commandList.getItems().stream().toList();

        if (registeredCommands.size() == 0) {
            registeredCommands = Command.registeredCommands.stream().toList();
        }

        removeAllRegisteredCommands();

        if (searchCommand.getCharacters().length() == 0) {
            addAllRegisteredCommands();
        } else {
            for (RegisteredCommand registeredCommand : registeredCommands) {
                if (registeredCommand.name.toLowerCase().contains(searchCommand.getCharacters().toString().toLowerCase())) {
                    commandList.getItems().add(registeredCommand);
                }
            }
        }
    }

    void initData(MainController mainController) {
        this.mainController = mainController;
    }
}