package com.example.macroproject.commands.logic;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.logicinterrupter.LogicInterrupter;
import com.example.macroproject.variables.StringVariable;
import com.example.macroproject.variables.Variable;

import java.io.Serializable;

public class IfCommand extends Command {
    protected StringVariable inputCommand;
    protected Command endIfCommand;

    public IfCommand(CommandFunction function, StringVariable inputCommand) {
        super(function);
        //TODO: add null checks for all commands
        this.inputCommand = inputCommand;
        this.endIfCommand = new EndIfCommand(function);
        function.addCommand(endIfCommand);
    }

    @Override
    public void runAfterInit() {
        function.changeCommandPosition(function.getCommandIndex(this), function.getCommandIndex(endIfCommand));
    }

    @Override
    public void run() {
        Object output = LogicInterrupter.evalString(inputCommand.getValue());

        if (output instanceof String) {
            Variable outputVariable = Variable.checkVariableReference((String) output, Boolean.class, true);
            if (outputVariable != null) {
                output = outputVariable.getValue();
            }
        }

        if (!((Boolean) output)) {
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

    @Override
    public void cleanSelfToBeRemoved() {
        try {
            function.removeCommand(endIfCommand);
        } catch (Exception ignored) {}
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("If", IfCommand.class, "commands/if.fxml");
    }
}
