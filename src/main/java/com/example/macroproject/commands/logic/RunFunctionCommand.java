package com.example.macroproject.commands.logic;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.variables.StringVariable;

public class RunFunctionCommand extends Command {
    private StringVariable functionName;
    public RunFunctionCommand(CommandFunction function, StringVariable functionName) {
        super(function);
        this.functionName = functionName;
    }

    @Override
    public void run() {
        CommandFunction commandFunction =  CommandFunction.getFunction(functionName.getValue());
        if (commandFunction != null) {
            commandFunction.start(false);
        }
    }

    @Override
    public String toString() {
        return String.format("Run Function: %s", functionName.getValue());
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("Run Function", RunFunctionCommand.class, "commands/runfunction.fxml");
    }
}
