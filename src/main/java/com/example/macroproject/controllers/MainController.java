package com.example.macroproject.controllers;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.controllers.commands.CommandController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;


public class MainController extends FXMLController {
    @FXML
    private Button removeCommandButton;
    @FXML
    private Button addCommandButton;
    @FXML
    private TabPane functionTabPane;

    @FXML
    public void initialize() {
        refreshFunctions();
    }

    private void removeAllFunctionTabs() {
        int amountOfTabs = functionTabPane.getTabs().stream().toList().size();
        for (int tabIndex = 1; tabIndex < amountOfTabs; tabIndex++) {
            functionTabPane.getTabs().remove(tabIndex);
        }
    }

    private void addCommandFunctionTab(CommandFunction commandFunction) {
        ListView<Command> listView = new ListView<>();
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, command, t1) -> enableRemoveCommandButton()
        );

        Tab functionTab = new Tab(commandFunction.getName(), listView);
        refreshTabCommands(functionTab);
        functionTabPane.getTabs().add(functionTab);
    }

    public void refreshTabCommands(Tab tab) {
        ListView<Command> listView = (ListView<Command>) tab.getContent();
        listView.getItems().removeAll(listView.getItems());
        for (Command command : CommandFunction.getFunction(tab.getText()).getFunctionCommands()) {
            listView.getItems().add(command);
        }
    }

    @FXML
    public void refreshFunctions() {
        removeAllFunctionTabs();

        for (CommandFunction commandFunction : CommandFunction.getAllFunctions()) {
            addCommandFunctionTab(commandFunction);
        }
        disableRemoveCommandButton();
        functionTabPane.getSelectionModel().select(1);
    }

    @FXML
    protected void openCommandList() {
        Object[] stageAndLoader = openNewStage("command-list-view.fxml", "Commands", 200, 240);
        assert stageAndLoader != null;

        Stage stage = (Stage) stageAndLoader[0];
        FXMLLoader fxmlLaoder = (FXMLLoader) stageAndLoader[1];
        CommandListController controller = fxmlLaoder.getController();
        controller.initData(this);

        stage.setOnHiding(windowEvent -> enableAddCommandButton());

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

        stage.setOnHiding(windowEvent -> enableAddCommandButton());
        stage.show();
    }

    public Tab getSelectedTab() {
        return functionTabPane.getSelectionModel().getSelectedItem();
    }

    public ListView<Command> getSelectedListView() {
        return (ListView<Command>) getSelectedTab().getContent();
    }

    public CommandFunction getSelectedCommandFunction() {
        return CommandFunction.getFunction(getSelectedTab().getText());
    }

    public void addCommand(Command command) {
        getSelectedCommandFunction().addCommand(command);
        getSelectedListView().getItems().add(command);
        refreshTabCommands(getSelectedTab());
    }

    @FXML
    protected void removeCommand() {
        int index = getSelectedListView().getSelectionModel().getSelectedIndex();
        getSelectedCommandFunction().removeCommand(index);
        getSelectedListView().getItems().remove(index);

        if (getSelectedListView().getItems().size() == 0) {
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
        if (functionTabPane.getSelectionModel().getSelectedIndex() > 0) {
            getSelectedCommandFunction().start();
        }
    }

    @FXML
    protected void stopMacro() {
        CommandFunction.getMainFunction().stopMacro();
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