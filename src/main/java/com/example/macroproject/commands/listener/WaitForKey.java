package com.example.macroproject.commands.listener;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.listener.Listener;
import com.example.macroproject.variables.BooleanVariable;
import com.example.macroproject.variables.StringVariable;

public class WaitForKey extends Command {
    protected volatile BooleanVariable gotKey;
    protected volatile StringVariable listenKey;

    public WaitForKey(int startDelay, CommandFunction function, StringVariable listenKey) {
        super(startDelay, function);
        this.listenKey = listenKey;
    }

    @Override
    public void run() {
        Listener.getNextKey(this::listenerCallback, listenKey.getValue());

        while (!gotKey.getValue()) {
            Thread.onSpinWait();
        }
    }

    protected void listenerCallback() {
        gotKey.setValue(true);
    }

    @Override
    public String toString() {
        return null;
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("WaitForKey", WaitForKey.class, "commands/waitforkey.fxml");
    }
}
