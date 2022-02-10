package com.example.macroproject.commands;

import com.example.macroproject.listener.KeyInput;
import com.example.macroproject.listener.Listener;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;

import javax.print.attribute.SetOfIntegerSyntax;
import java.util.ArrayList;
import java.util.Iterator;
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
                toggleRun(true);
            }
        } else {
            if (enabling) {
                start(true);
            } else {
                stopMacro();
            }
        }
    }

    public void removeCommand(int index) {
        try {
            functionCommands.get(index).cleanSelfToBeRemoved();
            functionCommands.remove(index);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public Command getCommand(int index) {
        return functionCommands.get(index);
    }

    public void removeCommand(Command removeCommand) {
        int index = functionCommands.indexOf(removeCommand);
        functionCommands.get(index).cleanSelfToBeRemoved();
        // The index of the command could possibly have changed
        index = functionCommands.indexOf(removeCommand);
        functionCommands.remove(index);
    }

    private void run() {
        isRunning = true;
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        while (commandIndex.getValue() < functionCommands.size() && isRunning) {
            functionCommands.get(commandIndex.getValue()).start();
            commandIndex.addValue(1);
        }

        stopMacro();
    }

    public void start(boolean onNewThread) {
        if (!isRunning) {
            if (onNewThread) {
                Thread thread = new Thread(this::run);
                thread.start();
            } else {
                this.run();
            }
        }
    }

    public void toggleRun(boolean onNewThread) {
        if (isRunning) {
            stopMacro();
        } else {
            start(onNewThread);
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
        if (!isRunning) {
            toPosition = Math.max(0, Math.min(toPosition, functionCommands.size() - 1));
            fromPosition = Math.max(0, Math.min(fromPosition, functionCommands.size() - 1));

            Command command = functionCommands.get(fromPosition);
            functionCommands.remove(fromPosition);
            functionCommands.add(toPosition, command);
        }
    }

    public int getCommandIndex(Command command) {
        int i = 0;
        for (Command nextCommand : functionCommands) {
            if (command == nextCommand) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void setCommandIndex(int newIndex) {
        commandIndex.setValue(newIndex);
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
