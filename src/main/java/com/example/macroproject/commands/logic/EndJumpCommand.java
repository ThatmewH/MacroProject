package com.example.macroproject.commands.logic;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.variables.IntegerVariable;

public class EndJumpCommand extends Command {

    public EndJumpCommand(CommandFunction function) {
        super(function);
    }

    @Override
    public void run() {

    }

    @Override
    public String toString() {
        return "End Jump";
    }

}
