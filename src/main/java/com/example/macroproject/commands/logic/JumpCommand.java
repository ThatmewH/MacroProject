package com.example.macroproject.commands.logic;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.variables.IntegerVariable;

public class JumpCommand extends Command {
    private EndJumpCommand endJumpCommand;

    public JumpCommand(CommandFunction function) {
        super(function);
        this.endJumpCommand = new EndJumpCommand(function);
        function.addCommand(endJumpCommand);
    }

    @Override
    public void runAfterInit() {
        function.changeCommandPosition(function.getCommandIndex(this), function.getCommandIndex(endJumpCommand));
    }

    @Override
    public void run() {
        function.setCommandIndex(function.getCommandIndex(endJumpCommand));
    }

    @Override
    public String toString() {
        return String.format("Jump to line: %s", function.getCommandIndex(endJumpCommand));
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("Jump", JumpCommand.class, "commands/jump.fxml");
    }
}
