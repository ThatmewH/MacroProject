package com.example.macroproject.commands;

import com.example.macroproject.commands.click.ClickCommand;
import com.example.macroproject.commands.listener.WaitForKey;
import com.example.macroproject.commands.time.WaitCommand;

import java.io.File;
import java.util.ArrayList;

public abstract class Command {

    protected int startDelay;
    protected CommandFunction function;

    public static ArrayList<RegisteredCommand> registeredCommands = new ArrayList<>() {
        {
            add(ClickCommand.registerCommand());
            add(WaitCommand.registerCommand());
            add(WaitForKey.registerCommand());
        }
    };

    public Command(int startDelay, CommandFunction function) {
        if (function == null) {
            throw new IllegalArgumentException();
        }

        this.startDelay = startDelay;
        this.function = function;
    }

    abstract public void run();

    public void start() {
        try {
            Thread.sleep(startDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cleanSelfToBeRemoved() {

    }

    @Override
    public abstract String toString();

}
