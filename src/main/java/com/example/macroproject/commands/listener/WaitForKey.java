package com.example.macroproject.commands.listener;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.listener.KeyListener;
import com.example.macroproject.listener.Listener;
import com.example.macroproject.variables.BooleanVariable;
import com.example.macroproject.variables.StringVariable;

public class WaitForKey extends Command {
    protected volatile BooleanVariable gotKey = new BooleanVariable("", false);
    protected StringVariable listenKey;
    protected KeyListener keyListener;

    public WaitForKey(int startDelay, CommandFunction function, StringVariable listenKey) {
        super(startDelay, function);

        if (listenKey == null) {
            throw new IllegalArgumentException();
        }

        this.listenKey = listenKey;
        this.keyListener = new KeyListener(this::listenerCallback, listenKey);
    }

    @Override
    public void run() {
        keyListener.startListening();
        while (!gotKey.getValue() && function.isRunning()) {
            Thread.onSpinWait();
        }
        keyListener.stopListening();
        gotKey.setValue(false);
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

    @Override
    public void cleanSelfToBeRemoved() {
        super.cleanSelfToBeRemoved();
        keyListener.removeSelf();
    }
}
