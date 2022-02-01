package com.example.macroproject.controllers;

import com.example.macroproject.MacroController;
import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.controllers.commands.CommandController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;

public class MainController extends FXMLController {
    @FXML
    private ListView<Command> cmdListView;
    @FXML
    private Button removeCommandButton;
    @FXML
    private Button addCommandButton;

    @FXML
    public void initialize() {
        cmdListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Command>() {
                    @Override
                    public void changed(ObservableValue<? extends Command> observableValue, Command command, Command t1) {
                        enableRemoveCommandButton();
                    }
                }
        );
    }

    @FXML
    protected void openCommandList() {
        Object[] stageAndLoader = openNewStage("command-list-view.fxml", "Commands", 200, 240);
        assert stageAndLoader != null;

        Stage stage = (Stage) stageAndLoader[0];
        FXMLLoader fxmlLaoder = (FXMLLoader) stageAndLoader[1];
        CommandListController controller = fxmlLaoder.getController();
        controller.initData(this);

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                enableAddCommandButton();
            }
        });

        stage.show();
        disableAddCommandButton();
    }

    @FXML
    protected void openVariableList() {
        Object[] stageAndLoader = openNewStage("variable-list-view.fxml", "Variables", 400, 150);
        assert stageAndLoader != null;

        Stage stage = (Stage) stageAndLoader[0];
        FXMLLoader fxmlLaoder = (FXMLLoader) stageAndLoader[1];
        VariableListController controller = fxmlLaoder.getController();
        controller.initData(this);

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                enableAddCommandButton();
            }
        });
        stage.show();
    }

    public void addCommand(Command command) {
        cmdListView.getItems().add(command);
    }

    @FXML
    protected void removeCommand() {
        cmdListView.getItems().remove(cmdListView.getSelectionModel().getSelectedIndex());
        if (cmdListView.getItems().size() == 0) {
            disableRemoveCommandButton();
        }
    }

    @FXML
    protected void disableRemoveCommandButton() {
        removeCommandButton.setDisable(true);
    }

    @FXML
    protected void enableRemoveCommandButton() {
        removeCommandButton.setDisable(false);
    }

    @FXML
    protected void startMacro() {
        MacroController.start(
            getCommands()
        );
    }

    @FXML
    protected void stopMacro() {
        MacroController.stopMacro();
    }

    protected List<Command> getCommands() {
        return cmdListView.getItems();
    }

    void openNewCommandStage(RegisteredCommand registeredCommand) {
        Object[] stageAndLoader = openNewStage(registeredCommand.FXMLView, registeredCommand.name, 400, 150);
        if (stageAndLoader != null) {

            Stage stage = (Stage) stageAndLoader[0];
            FXMLLoader fxmlLaoder = (FXMLLoader) stageAndLoader[1];
            CommandController controller = fxmlLaoder.getController();

            stage.setWidth(controller.getStageWidth());
            stage.setHeight(controller.getStageHeight());

            controller.initData(this, stage.getScene());

            stage.show();
        }
    }

    private void disableAddCommandButton() {
        addCommandButton.setDisable(true);
    }

    private void enableAddCommandButton() {
        addCommandButton.setDisable(false);
    }
}