package com.example.macroproject.commands;

import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.Variable;

import java.util.ArrayList;
import java.util.List;

public class CommandFunction {
    static ArrayList<CommandFunction> functions = new ArrayList<>();

    protected String name;
    protected boolean isRunning = false;
    protected IntegerVariable commandIndex;
    protected List<Command> functionCommands = new ArrayList<>();

    public CommandFunction(String name) {
        this.name = name;
        commandIndex = new IntegerVariable("commandIndex" + name, 0);
        Variable.addNewVariable(commandIndex);
        CommandFunction.addFunction(this);
    }

    public void addCommand(Command command) {
        functionCommands.add(command);
    }

    private void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isRunning = true;
        while (commandIndex.getValue() < functionCommands.size() && isRunning) {
            functionCommands.get(commandIndex.getValue()).start();
            commandIndex.addValue(1);
        }

        stopMacro();
    }

    public void start() {
        if (!isRunning) {
            Thread thread = new Thread(this::run);
            thread.start();
        }
    }

    public void setFunctionCommands(List<Command> newFunctionCommands) {
        this.functionCommands = newFunctionCommands;
    }

    public void stopMacro() {
        isRunning = false;
        commandIndex.setValue(0);
    }

    public static void addFunction(CommandFunction function) {
        if (getFunction(function.name) == null) {
            functions.add(function);
        } else {
            throw new IllegalArgumentException("Function Already Exists");
        }
    }

    public static CommandFunction getFunction(String name) {
        for (CommandFunction commandFunction : functions) {
            if (commandFunction.name.equals(name)) {
                return commandFunction;
            }
        }
        return null;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public static CommandFunction getMainFunction() {
        return getFunction("MainFunction");
    }

    public static void CreateMainFunction() {
        CommandFunction mainFunction = new CommandFunction("MainFunction");
    }
}
