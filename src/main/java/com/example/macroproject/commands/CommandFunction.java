package com.example.macroproject.commands;

import com.example.macroproject.listener.KeyInput;
import com.example.macroproject.listener.Listener;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;

import javax.print.attribute.SetOfIntegerSyntax;
import java.util.ArrayList;
import java.util.List;

public class CommandFunction {
    static ArrayList<CommandFunction> functions = new ArrayList<>();

    protected String name;
    protected volatile boolean isRunning = false;
    protected IntegerVariable commandIndex;
    protected List<Command> functionCommands = new ArrayList<>();
    protected KeyInput startListener = new KeyInput(() -> onEnableListener(true), new StringVariable("", ""));
    protected KeyInput stopListener = new KeyInput(() -> onEnableListener(false), new StringVariable("", ""));

    public CommandFunction(String name) {
        this.name = name;
        commandIndex = new IntegerVariable("commandIndex" + name, 0);
        Variable.addNewVariable(commandIndex);
        CommandFunction.addFunction(this);

        startListener.startListening();
        stopListener.startListening();
    }

    public void addCommand(Command command) {
        functionCommands.add(command);
    }

    public void onEnableListener(boolean enabling) {
        if (startListener.getListeningKey().getValue().equals(stopListener.getListeningKey().getValue())) {
            if (enabling) {
                toggleRun();
            }
        } else {
            if (enabling) {
                start();
            } else {
                stopMacro();
            }
        }
    }

    public void removeCommand(int index) {
        functionCommands.get(index).cleanSelfToBeRemoved();
        functionCommands.remove(index);
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

    public void toggleRun() {
        if (isRunning) {
            stopMacro();
        } else {
            start();
        }
    }

    public void setFunctionCommands(List<Command> newFunctionCommands) {
        this.functionCommands = newFunctionCommands;
    }

    public void stopMacro() {
        isRunning = false;
        commandIndex.setValue(0);
    }

    public String getName() {
        return name;
    }

    public List<Command> getFunctionCommands() {
        return functionCommands;
    }

    public KeyInput getStartListener() {
        return startListener;
    }

    public KeyInput getStopListener() {
        return stopListener;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void changeCommandPosition(int fromPosition, int toPosition) {
        toPosition = Math.max(0, Math.min(toPosition, functionCommands.size() - 1));
        fromPosition = Math.max(0, Math.min(fromPosition, functionCommands.size() - 1));

        Command command = functionCommands.get(fromPosition);
        functionCommands.remove(fromPosition);
        functionCommands.add(toPosition, command);
    }

    public int getCommandIndex(Command command) {
        for (int i = 0; i < functionCommands.size(); i++) {
            if (command == functionCommands.get(i)) {
                return i;
            }
        }
        return -1;
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

    public static void CreateMainFunction() {
        createFunction("MainFunction");
    }

    public static List<CommandFunction> getAllFunctions() {
        return functions;
    }

    public static void createFunction(String functionName) {
        CommandFunction newCommandFunction = new CommandFunction(functionName);
    }

    public static void removeFunction(String functionName) {
        CommandFunction function = getFunction(functionName);
        functions.remove(function);
        Variable.deleteVariable(function.commandIndex.name);

        for (int i = 0; i < function.getFunctionCommands().size(); i++) {
            function.removeCommand(i);
        }

        function.commandIndex = null;
        function.functionCommands = null;
        Listener.removeKeyListener(function.getStartListener());
        Listener.removeKeyListener(function.getStopListener());
        function = null;
    }
}
