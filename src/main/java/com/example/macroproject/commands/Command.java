package com.example.macroproject.commands;

import com.example.macroproject.commands.click.ClickCommand;
import com.example.macroproject.commands.time.WaitCommand;

import java.io.File;
import java.util.ArrayList;

public abstract class Command {

    protected int startDelay;
    protected CommandFunction function;

    public Command(int startDelay, CommandFunction function) {
        this.startDelay = startDelay;
        this.function = function;
    }

    public static ArrayList<RegisteredCommand> registeredCommands = new ArrayList<>() {
        {
            add(ClickCommand.registerCommand());
            add(WaitCommand.registerCommand());
        }
    };

    abstract public void run();

    public void start() {
        try {
            Thread.sleep(startDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        run();
    }

    @Override
    public abstract String toString();

}
