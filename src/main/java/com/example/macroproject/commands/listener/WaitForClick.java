package com.example.macroproject.commands.listener;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.listener.KeyInput;
import com.example.macroproject.listener.MouseInput;
import com.example.macroproject.variables.BooleanVariable;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.StringVariable;

public class WaitForClick extends Command {
    protected volatile BooleanVariable gotKey = new BooleanVariable("", false);
    protected IntegerVariable listenMouseButton;
    protected MouseInput mouseListener;

    public WaitForClick(CommandFunction function, IntegerVariable listenKey, IntegerVariable outputMouseX
            , IntegerVariable outputMouseY, IntegerVariable outputClickNum) {
        super(function);

        if (listenKey == null || outputMouseX == null || outputMouseY == null || outputClickNum == null) {
            throw new IllegalArgumentException();
        }

        this.listenMouseButton = listenKey;
        this.mouseListener = new MouseInput(this::listenerCallback, listenKey, outputMouseX, outputMouseY, outputClickNum);
    }

    @Override
    public void run() {
        mouseListener.startListening();
        while (!gotKey.getValue() && function.isRunning()) {
            Thread.onSpinWait();
        }
        mouseListener.stopListening();
        gotKey.setValue(false);
    }

    protected void listenerCallback() {
        gotKey.setValue(true);
    }

    @Override
    public String toString() {
        return String.format("WaitForKey: %s", listenMouseButton.getValue());
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("WaitForMouseClick", WaitForClick.class, "commands/waitforclick.fxml");
    }

    @Override
    public void cleanSelfToBeRemoved() {
        super.cleanSelfToBeRemoved();
        mouseListener.removeSelf();
    }
}
