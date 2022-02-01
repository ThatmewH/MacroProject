package com.example.macroproject.commands;

import com.example.macroproject.commands.click.ClickCommand;
import com.example.macroproject.commands.time.WaitCommand;

import java.io.File;
import java.util.ArrayList;

public abstract class Command {

    private int startDelay;

    public Command(int startDelay) {
        this.startDelay = startDelay;
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
