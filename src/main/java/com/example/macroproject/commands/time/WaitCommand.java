package com.example.macroproject.commands.time;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.variables.IntegerVariable;

public class WaitCommand extends Command {

    protected IntegerVariable duration;

    public WaitCommand(IntegerVariable waitTime, CommandFunction function) {
        super(function);
        duration = waitTime;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(duration.getValue());
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("Wait: %sms", duration.getValue());
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("Wait", WaitCommand.class, "commands/wait.fxml");
    }
}
