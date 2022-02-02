package com.example.macroproject.commands.listener;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.listener.Listener;
import com.example.macroproject.variables.BooleanVariable;
import com.example.macroproject.variables.StringVariable;

public class WaitForKey extends Command {
    protected volatile BooleanVariable gotKey = new BooleanVariable("", false);
    protected StringVariable listenKey;
    protected Listener listener = new Listener();

    public WaitForKey(int startDelay, CommandFunction function, StringVariable listenKey) {
        super(startDelay, function);
        this.listenKey = listenKey;
    }

    @Override
    public void run() {
        listener.getNextKey(this::listenerCallback, listenKey.getValue());

        while (!gotKey.getValue() && function.isRunning()) {
            Thread.onSpinWait();
            if (!function.isRunning()) {
                listener.stopListening();
                return;
            }
        }

        gotKey.setValue(false);
        listener.stopListening();
    }

    protected void listenerCallback() {
        gotKey.setValue(true);
    }

    @Override
    public String toString() {
        return String.format("WaitForKey: %s", listenKey.getValue());
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("WaitForKey", WaitForKey.class, "commands/waitforkey.fxml");
    }
}
