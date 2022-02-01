package com.example.macroproject;

import com.example.macroproject.commands.Command;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.Variable;

import java.util.List;

public class MacroController {
    public static boolean isRunning = false;
    static List<Command> commands;
    static IntegerVariable commandIndex;

    static void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isRunning = true;
        while (commandIndex.getValue() < commands.size() && isRunning) {
            commands.get(commandIndex.getValue()).start();
            commandIndex.addValue(1);
        }

        stopMacro();
    }

    public static void start(List<Command> commandArrayList) {
        if (!isRunning) {
            commands = commandArrayList;
            Thread thread = new Thread(MacroController::run);
            thread.start();
        }
    }

    public static void stopMacro() {
        isRunning = false;
        commandIndex.setValue(0);
    }

    public static void init() {
        commandIndex = new IntegerVariable("commandIndex", 0);
        Variable.addNewVariable(commandIndex);
    }
}
