package com.example.macroproject.controllers;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.controllers.commands.CommandController;
import com.example.macroproject.javafxelements.DraggableCell;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ListIterator;


public class MainController extends FXMLController {

    @FXML
    private Button removeCommandButton;
    @FXML
    private Button addCommandButton;
    @FXML
    private TabPane functionTabPane;
    @FXML
    private Tab newFunctionTab;

    @FXML
    public void initialize() {
        refreshFunctions();
        functionTabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, tab, t1) -> {
            if (t1 == newFunctionTab) {
                openNewFunctionStage();
                functionTabPane.getSelectionModel().select(tab);
            }
            disableRemoveCommandButton();
            enableAddCommandButton();
        });
    }

    public int getSelectedCommandIndex() {
        Command command = getSelectedListView().getSelectionModel().getSelectedItem();
        return getSelectedCommandFunction().getCommandIndex(command);
    }

    public void switchCommand(int indexFirst, int indexSecond) {
        getSelectedCommandFunction().changeCommandPosition(indexFirst, indexSecond);
        refreshTabCommands(getSelectedTab());
    }

    private void removeAllFunctionTabs() {
        for (ListIterator<Tab> listIterator = functionTabPane.getTabs().listIterator(1); listIterator.hasNext();) {
            listIterator.next();
            listIterator.remove();
        }
    }

    private void addCommandFunctionTab(CommandFunction commandFunction) {
        ListView<Command> listView = new ListView<>();
        listView.setCellFactory(param -> new DraggableCell(getSelectedCommandFunction(), this));

        listView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, command, t1) ->  {
                    int index = listView.getItems().indexOf(t1);
                    enableRemoveCommandButton();
                }
        );



        Tab functionTab = new Tab(commandFunction.getName(), listView);

        refreshTabCommands(functionTab);
        functionTabPane.getTabs().add(functionTab);

        if (commandFunction.getName().equals("MainFunction")) {
            functionTab.setClosable(false);
        } else {
            functionTab.setOnClosed(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    event.consume();

                    removeFunction(((Tab) event.getSource()).getText());
                }
            });
        }
    }

    protected void removeFunction(String functionName) {
        CommandFunction.removeFunction(functionName);
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
        newFunctionTab.setDisable(true);
        removeAllFunctionTabs();

        for (CommandFunction commandFunction : CommandFunction.getAllFunctions()) {
            addCommandFunctionTab(commandFunction);
        }
        disableRemoveCommandButton();
        newFunctionTab.setDisable(false);
        functionTabPane.getSelectionModel().select(functionTabPane.getTabs().size() - 1);
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
        Object[] stageAndLoader = openNewStage("function-settings-view.fxml", "Variables", 400, 150);
        assert stageAndLoader != null;

        Stage stage = (Stage) stageAndLoader[0];
        FXMLLoader fxmlLoader = (FXMLLoader) stageAndLoader[1];
        FunctionSettingsController controller = fxmlLoader.getController();
        controller.initData(this, getSelectedCommandFunction());

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
        command.runAfterInit();
        refreshTabCommands(getSelectedTab());
    }

    @FXML
    protected void removeCommand() {
        int index = getSelectedListView().getSelectionModel().getSelectedIndex();
        Command command = getSelectedCommandFunction().getCommand(index);

        getSelectedCommandFunction().removeCommand(command);
        getSelectedListView().getItems().remove(index);

        if (getSelectedListView().getItems().size() == 0) {
            disableRemoveCommandButton();
        }

        refreshTabCommands(getSelectedTab());
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
        CommandFunction commandFunction = getSelectedCommandFunction();
        if (commandFunction != null) {
            commandFunction.start(true);
        }
    }

    @FXML
    protected void stopMacro() {
        getSelectedCommandFunction().stopMacro();
    }

    void openNewCommandStage(RegisteredCommand registeredCommand) {
        Object[] stageAndLoader = openNewStage(registeredCommand.FXMLView, registeredCommand.name, 400, 150);
        if (stageAndLoader != null) {

            Stage stage = (Stage) stageAndLoader[0];
            FXMLLoader fxmlLaoder = (FXMLLoader) stageAndLoader[1];
            CommandController controller = fxmlLaoder.getController();

            stage.setWidth(controller.getStageWidth());
            stage.setHeight(controller.getStageHeight());

            controller.initData(this, stage.getScene(), getSelectedCommandFunction());

            stage.show();
        }
    }

    private void disableAddCommandButton() {
        addCommandButton.setDisable(true);
    }

    private void enableAddCommandButton() {
        addCommandButton.setDisable(false);
    }

    @FXML
    protected void openNewFunctionStage() {
        Object[] stageAndLoader = openNewStage("new-function-view.fxml", "New Function", 200, 75);
        Stage stage = (Stage) stageAndLoader[0];
        FXMLLoader loader = (FXMLLoader) stageAndLoader[1];

        NewFunctionController controller = (NewFunctionController) loader.getController();
        controller.initData(this);

        stage.show();
    }

    public void addNewFunction(String newFunctionName) {
        CommandFunction.createFunction(newFunctionName);
        refreshFunctions();
    }
}