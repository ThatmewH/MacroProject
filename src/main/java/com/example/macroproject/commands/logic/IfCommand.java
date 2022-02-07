package com.example.macroproject.commands.logic;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.logicinterrupter.LogicInterrupter;
import com.example.macroproject.variables.StringVariable;

public class IfCommand extends Command {
    protected StringVariable inputCommand;
    protected Command endIfCommand;

    public IfCommand(CommandFunction function, StringVariable inputCommand) {
        super(function);
        this.inputCommand = inputCommand;
        this.endIfCommand = new EndIfCommand(function);
        function.addCommand(endIfCommand);
    }

    @Override
    public void run() {
        if ((Boolean) LogicInterrupter.evalString(inputCommand.getValue())) {
            return;
        } else {
            int newCommandIndex = function.getCommandIndex(endIfCommand);
            if (newCommandIndex != -1) {
                function.setCommandIndex(newCommandIndex);
            }
        }
    }

    @Override
    public String toString() {
        return "If: " + inputCommand.getValue();
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("If", IfCommand.class, "commands/if.fxml");
    }

    @Override
    public void cleanSelfToBeRemoved() {
        function.removeCommand(endIfCommand);
    }
}
