package com.example.macroproject.commands;

import com.example.macroproject.commands.click.ClickCommand;
import com.example.macroproject.commands.listener.WaitForClick;
import com.example.macroproject.commands.listener.WaitForKey;
import com.example.macroproject.commands.logic.IfCommand;
import com.example.macroproject.commands.logic.JumpCommand;
import com.example.macroproject.commands.logic.LogicCommand;
import com.example.macroproject.commands.time.WaitCommand;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Command implements Serializable {
    protected CommandFunction function;

    public static ArrayList<RegisteredCommand> registeredCommands = new ArrayList<>() {
        {
            add(ClickCommand.registerCommand());
            add(WaitCommand.registerCommand());
            add(WaitForKey.registerCommand());
            add(WaitForClick.registerCommand());
            add(IfCommand.registerCommand());
            add(LogicCommand.registerCommand());
            add(JumpCommand.registerCommand());
        }
    };

    public Command(CommandFunction function) {
        if (function == null) {
            throw new IllegalArgumentException();
        }
        this.function = function;

    }

    public void runAfterInit() {

    }

    abstract public void run();

    public void start() {
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Removes any variables in this class that may be referenced somewhere else (Except for custom Variables)
     * Usually will not need to be called
     */
    public void cleanSelfToBeRemoved() {

    }

    @Override
    public abstract String toString();

}
