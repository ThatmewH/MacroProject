package com.example.macroproject.commands.logic;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.logicinterrupter.LogicInterrupter;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;

import java.io.Serializable;

public class LogicCommand extends Command {
    protected StringVariable inputCommand;

    public LogicCommand(CommandFunction function, StringVariable inputCommand) {
        super(function);
        this.inputCommand = inputCommand;
    }

    @Override
    public void run() {
        LogicInterrupter.evalString(inputCommand.getValue());
    }

    @Override
    public String toString() {
        return "Logic: " + inputCommand.getValue();
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("Logic", LogicCommand.class, "commands/logic.fxml");
    }
}
